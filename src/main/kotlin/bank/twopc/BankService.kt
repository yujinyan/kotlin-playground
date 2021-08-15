package bank.twopc

import java.util.concurrent.*
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.concurrent.thread

/**
 * Simulates performing two-phase commits as described in
 * https://docs.mongodb.com/v3.4/tutorial/perform-two-phase-commits/
 */

data class Transaction(
  val id: Int,
  val from: Account,
  val to: Account,
  val amount: Int,
  val state: State
) {
  enum class State { Initial, Pending, Applied, Done }
}

interface Account {
  val id: Int
  val balance: Int

  /**
   * [credit] and [debit] are helper methods mainly used for initializing accounts.
   */
  fun credit(amount: Int)
  fun debit(amount: Int)

  /**
   * @param id: transaction id
   */
  fun apply(id: Int, amount: Int)

  /**
   * @param id: transaction id
   */
  fun commit(id: Int)
}

interface BankService {
  fun transfer(from: Account, to: Account, amount: Int): Result<Transaction>
}

class RemoteAccount(private val delegate: Account) : Account {
  private val disconnected = AtomicBoolean(false)
  override val id: Int get() = throwIfDisconnected { delegate.id }
  override val balance: Int get() = throwIfDisconnected { delegate.balance }
  override fun credit(amount: Int) = throwIfDisconnected { delegate.credit(amount) }
  override fun debit(amount: Int) = throwIfDisconnected { delegate.debit(amount) }
  override fun apply(id: Int, amount: Int): Unit = throwIfDisconnected { delegate.apply(id, amount) }
  override fun commit(id: Int): Unit = throwIfDisconnected { delegate.commit(id) }
  fun disconnect() = disconnected.set(true)
  fun reconnect() = disconnected.set(false)
  private fun <T> throwIfDisconnected(block: () -> T): T =
    if (disconnected.get()) throw error("Account ${delegate.id} offline")
    else block()
}

fun main() {
  val account1 = RemoteAccount(AccountImpl(1).apply { credit(100) })
  val account2 = RemoteAccount(AccountImpl(2).apply { credit(100) })
  val executor = Executors.newFixedThreadPool(500)
  val bank = BankServiceImpl(executor)

  val times = 5000
  val startGate = CountDownLatch(1)
  repeat(times) {
    executor.submit {
      startGate.await()
      val r = ThreadLocalRandom.current()
      val from = if (r.nextBoolean()) account1 else account2
      val to = if (from == account1) account2 else account1
      runCatching { bank.transfer(from, to, r.nextInt(10, 50)) }
    }
  }

  // Randomly disconnects accounts. (Simulates network errors.)
  repeat(20) {
    executor.submit {
      startGate.await()
      account1.disconnect()
      Thread.sleep(ThreadLocalRandom.current().nextInt(10, 20).toLong())
      account1.reconnect()
    }
    executor.submit {
      startGate.await()
      account2.disconnect()
      Thread.sleep(ThreadLocalRandom.current().nextInt(10, 20).toLong())
      account2.reconnect()
    }
  }

  val observer = thread {
    startGate.await()
    try {
      while (Thread.currentThread().isAlive) {
        runCatching {
          val b1: Int = account1.balance
          val b2: Int = account2.balance
          if (b1 + b2 != 200) {
            println("inconsistency observed: account1 $b1, account2 $b2, sum ${b1 + b2}")
          }
        }.onFailure { println(it.message) }
        Thread.sleep(1)
      }
    } catch (e: InterruptedException) {
    }
  }

  startGate.countDown()

  executor.shutdown()
  executor.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS)
  observer.interrupt()

  require(account1.balance + account2.balance == 200)
}