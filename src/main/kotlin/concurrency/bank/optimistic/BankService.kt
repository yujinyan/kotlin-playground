package concurrency.bank.optimistic

import java.util.concurrent.Executor
import java.util.concurrent.atomic.AtomicInteger


/**
 * Reference JCIP p.207
 */

interface HasId {
  val id: Int
}

data class Transaction(
  override val id: Int,
  val from: Account,
  val to: Account,
  val amount: Int,
  val status: Status,
) : HasId {
  enum class Status(val value: Int) {
    New(0),
    DebitSuccess(1),
    TxSuccess(3)
  }
}

interface Account : HasId {
  val balance: Int
  fun compareAndSet(old: Int, new: Int): Boolean
}

open class AccountImpl(
  override val id: Int
) : Comparable<Account>, HasId, Account {
  private val _balance = AtomicInteger(0)

  override fun compareAndSet(old: Int, new: Int): Boolean {
    beforeService()
    return _balance.compareAndSet(old, new)
  }

  protected open fun beforeService() {
    // Implement by children.
  }

  fun credit(amount: Int) {
    _balance.getAndUpdate { it + amount }
  }

  override val balance get() = _balance.get()

  override fun compareTo(other: Account): Int = id - other.id

  override fun toString(): String {
    return "Account(id=${id}, balance=${_balance.get()})"
  }
}

class RemoteAccount(id: Int) : AccountImpl(id) {
  @Volatile
  private var isDisconnected = false

  fun disconnect() {
    isDisconnected = true
  }

  fun reconnect() {
    isDisconnected = false
  }

  override fun beforeService() {
    if (isDisconnected) {
      error("whoops, account $id is down")
    }
  }
}

open class AbstractRepository<T : HasId> : Iterable<T> {
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

  override fun iterator(): Iterator<T> = data.values.iterator()
}

class TransactionRepository : AbstractRepository<Transaction>()

class BankService(
  private val transactions: TransactionRepository,
  private val executor: Executor
) {
  abstract class TransferException(val tx: Transaction, msg: String?) : RuntimeException(msg)
  class InsufficientFund(tx: Transaction) : TransferException(tx, null)
  class RemoteException(tx: Transaction, msg: String?) : TransferException(tx, msg)

  private val retryTimes = 5

  fun transfer(from: Account, to: Account, amount: Int): Result<Transaction> {
    val tx = Transaction(transactions.nextId(), from, to, amount, Transaction.Status.New)
    transactions.save(tx)
    return runCatching {
      transfer(tx)
    }.onFailure {
      if (it !is InsufficientFund) {
        executor.execute { retry(tx) }
      }
      if (it !is TransferException) {
        return Result.failure(RemoteException(tx, it.message))
      }
    }
  }

  private fun transfer(tx: Transaction): Transaction {
    val from = tx.from
    val to = tx.to
    val amount = tx.amount
    when (tx.status) {
      Transaction.Status.New -> {
        val nTx = tx.copy(status = Transaction.Status.DebitSuccess).also {
          transactions.save(it)
        }
        for (i in 1..retryTimes + 1) {
          if (i == retryTimes + 1) throw RemoteException(tx, "debit failed")
          val fromBalance = from.balance
          val nBalance = fromBalance - amount
          if (nBalance < 0) throw InsufficientFund(tx)
          if (from.compareAndSet(fromBalance, fromBalance - amount)) break
        }
        return transfer(nTx)
      }
      Transaction.Status.DebitSuccess -> {
        val nTx = tx.copy(status = Transaction.Status.TxSuccess).also {
          transactions.save(it)
        }
        for (i in 1..retryTimes + 1) {
          if (i == retryTimes + 1) throw RemoteException(tx, "credit failed")
          val toBalance = to.balance
          if (to.compareAndSet(toBalance, toBalance + amount)) break
        }
        return transfer(nTx)
      }
      Transaction.Status.TxSuccess -> return tx
    }
  }

  private fun retry(tx: Transaction) {
    var t = tx
    while (t.status != Transaction.Status.TxSuccess) {
      t = try {
        transfer(tx)
      } catch (e: InsufficientFund) {
        return
      } catch (e: TransferException) {
        e.tx
      } catch (e: Throwable) {
        t
      }
      Thread.sleep(100)
    }
  }
}

