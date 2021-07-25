import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CountDownLatch
import java.util.concurrent.ThreadLocalRandom
import java.util.concurrent.atomic.AtomicInteger
import kotlin.concurrent.thread

/**
 * Reference JCIP p.207
 */

interface HasId {
  val id: Int
}

data class Transaction(
  override val id: Int,
  val fromId: Int,
  val toId: Int,
  val amount: Int,
  val status: Status,
) : HasId {
  enum class Status(val value: Int) {
    New(0),
    DebitSuccess(1),
    TxSuccess(3)
  }
}

class Account(
  override val id: Int
) : Comparable<Account>, HasId {
  private val balance = AtomicInteger(0)

  fun compareAndSet(old: Int, new: Int): Boolean {
    return balance.compareAndSet(old, new)
  }

  fun credit(amount: Int) {
    balance.getAndUpdate { it + amount }
  }

  fun getBalance(): Int {
    return balance.get()
  }

  override fun compareTo(other: Account): Int = id - other.id

  override fun toString(): String {
    return "Account(id=${id}, balance=${balance.get()})"
  }
}

open class AbstractRepository<T : HasId> {
  private var curId = 0
  private val data = mutableMapOf<Int, T>()

  @Synchronized
  fun findById(id: Int): T? {
    return data[id]
  }

  @Synchronized
  fun nextId(): Int = curId++

  @Synchronized
  fun save(item: T) {
    data[item.id] = item
  }
}

class TransactionRepository : AbstractRepository<Transaction>()
class AccountRepository : AbstractRepository<Account>() {
  fun compareAndUpdate(id: Int, expect: Int, update: Int): Boolean {
    val account = findById(id)!!
    return account.compareAndSet(expect, update)
  }
}

interface AccountRepositoryRouter {
  fun routeById(): AccountRepository
}


class BankService(
  private val accounts: AccountRepository,
  private val transactions: TransactionRepository
) {

  data class TransferRequest(
    val fromId: Int,
    val toId: Int,
    val amount: Int
  )

  class TransferException(msg: String) : RuntimeException(msg)
  class InsufficientFund() : RuntimeException()

  private val retryTimes = 5

  fun transfer(request: TransferRequest): Result<Unit> {
    val from = accounts.findById(request.fromId)
      ?: return Result.failure(TransferException("from account id ${request.fromId} not found"))
    val to = accounts.findById(request.toId)
      ?: return Result.failure(TransferException("to account id ${request.toId} not found"))

    val tx = Transaction(
      transactions.nextId(),
      from.id, to.id,
      request.amount, Transaction.Status.New
    )
    transactions.save(tx)


    for (i in 1..retryTimes + 1) {
      if (i > 1) println("debit retry $i")
      if (i == retryTimes + 1) return Result.failure(TransferException("debit failed"))
      val fromBalance = from.getBalance()
      val nBalance = fromBalance - request.amount
      if (nBalance < 0) return Result.failure(InsufficientFund())
      if (from.compareAndSet(fromBalance, fromBalance - request.amount)) break
    }

    transactions.save(tx.copy(status = Transaction.Status.DebitSuccess))


    for (i in 1..retryTimes + 1) {
      if (i > 1) println("credit retry $i")
      if (i == retryTimes + 1) return Result.failure(TransferException("credit failed"))
      val toBalance = to.getBalance()
      if (to.compareAndSet(toBalance, toBalance + request.amount)) break
    }

    transactions.save(tx.copy(status = Transaction.Status.TxSuccess))

    return Result.success(Unit)
  }
}


fun main() {
  val transactions = TransactionRepository()
  val accounts = AccountRepository()
  val bank = BankService(accounts, transactions)

  val account1 = Account(1).apply { credit(100) }.also { accounts.save(it) }
  val account2 = Account(2).apply { credit(100) }.also { accounts.save(it) }

  // after tx1 - 1: 50, 2: 150
  // after tx2 - 1: 60, 2: 140
  val t1 = thread {
    bank.transfer(BankService.TransferRequest(1, 2, 50)).getOrThrow()
  }
  val t2 = thread {
    bank.transfer(BankService.TransferRequest(2, 1, 10)).getOrThrow()
  }

  t1.join()
  t2.join()
  println(account1)
  println(account2)

  val times = 1000
  val startGate = CountDownLatch(1)
  val endGate = CountDownLatch(1000)
  val failedStats = ConcurrentHashMap<String, Int>()

  repeat(times) {
    thread {
      startGate.await()
      val r = ThreadLocalRandom.current()
      val from = if (r.nextBoolean()) account1 else account2
      val to = if (from == account1) account2 else account1
      bank.transfer(BankService.TransferRequest(from.id, to.id, r.nextInt(10, 50))).getOrElse {
        failedStats.compute(it.toString()) { _, v: Int? -> (v ?: 0) + 1 }
      }
      endGate.countDown()
    }
  }

  startGate.countDown()
  endGate.await()

  println(account1)
  println(account2)
  assert(account1.getBalance() + account2.getBalance() == 200)
  println(failedStats)
}
