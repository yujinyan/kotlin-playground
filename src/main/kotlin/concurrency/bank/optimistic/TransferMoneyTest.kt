package concurrency.bank.optimistic

import java.util.concurrent.*
import kotlin.concurrent.thread

fun main() {
  val transactions = TransactionRepository()
  val bank = BankService(transactions, Executors.newCachedThreadPool())

  val account1 = AccountImpl(1).apply { credit(100) }
  val account2 = AccountImpl(2).apply { credit(100) }

  // after tx1 - 1: 50, 2: 150
  // after tx2 - 1: 60, 2: 140
  val t1 = thread {
    bank.transfer(account1, account2, 50).getOrThrow()
  }
  val t2 = thread {
    bank.transfer(account2, account1, 10).getOrThrow()
  }

  t1.join()
  t2.join()
  println(account1)
  println(account2)

  val times = 1000
  val startGate = CountDownLatch(1)
  val endGate = CountDownLatch(times)
  val failedStats = ConcurrentHashMap<String, Int>()

  repeat(times) {
    thread {
      startGate.await()
      val r = ThreadLocalRandom.current()
      val from = if (r.nextBoolean()) account1 else account2
      val to = if (from == account1) account2 else account1
      bank.transfer(from, to, r.nextInt(10, 50)).getOrElse {
        failedStats.compute(it.toString()) { _, v: Int? -> (v ?: 0) + 1 }
      }
      endGate.countDown()
    }
  }

  startGate.countDown()
  endGate.await()

  println(account1)
  println(account2)
  require(account1.balance + account2.balance == 200)
  println("failed stats: $failedStats")
}
