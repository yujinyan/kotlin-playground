package concurrency.bank.optimistic

import java.util.concurrent.*
import kotlin.concurrent.thread

private fun observe(
  account1: Account,
  account2: Account,
  transactions: TransactionRepository
) {
  println("account1: ${account1.balance}, account2: ${account2.balance}")

  val remaining = transactions.asSequence()
    .filter { it.status == Transaction.Status.DebitSuccess }
    .sumOf { it.amount }
    .also { println("remaining $it") }

  val total = account1.balance + account2.balance + remaining == 200
  val count = transactions.count { it.status == Transaction.Status.TxSuccess }
  println("total money: $total, success count $count")
}

fun main() {
  val executor = Executors.newCachedThreadPool()
  val transactions = TransactionRepository()
  val bank = BankService(transactions, executor)

  val account1 = RemoteAccount(1).apply { credit(100) }
  val account2 = RemoteAccount(2).apply { credit(100) }

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

  thread {
    Thread.sleep(50)
    account2.disconnect()
    Thread.sleep(70)
    account2.reconnect()
  }


  endGate.await()

  observe(account1, account2, transactions)
  println("failed stats: $failedStats")

  executor.shutdown()
  executor.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS)
  observe(account1, account2, transactions)
}