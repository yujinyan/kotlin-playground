package concurrency.bank.twopc

import concurrency.bank.twopc.Transaction.State.*
import java.util.concurrent.Executor
import java.util.concurrent.atomic.AtomicInteger

class AccountImpl(
  override val id: Int
) : Account {
  private val _balance = AtomicInteger()
  override val balance: Int get() = _balance.get()
  private val pending = mutableSetOf<Int>()

  override fun credit(amount: Int) {
    _balance.getAndUpdate { it + amount }
  }

  override fun debit(amount: Int) {
    _balance.getAndUpdate { it - amount }
  }

  override fun apply(id: Int, amount: Int) {
    synchronized(this) {
      if (id in pending) return
      _balance.getAndUpdate { it + amount }
      pending += id
    }
  }

  override fun commit(id: Int) {
    pending -= id
  }
}

class BankServiceImpl(
  private val executor: Executor
) : BankService {
  private val nextTxId = AtomicInteger(0)
  private val transactions = mutableMapOf<Int, Transaction>()

  private fun save(tx: Transaction) {
    transactions[tx.id] = tx
  }

  private fun process(tx: Transaction): Transaction {
    val from = tx.from
    val to = tx.to
    return when (tx.state) {
      Initial -> process(
        tx.copy(state = Pending).also { save(it) }
      )
      Pending -> {
        from.apply(tx.id, -tx.amount)
        to.apply(tx.id, tx.amount)
        process(tx.copy(state = Applied).also { save(it) })
      }
      Applied -> {
        from.commit(tx.id)
        to.commit(tx.id)
        process(tx.copy(state = Done).also { save(it) })
      }
      Done -> tx
    }
  }

  override fun transfer(from: Account, to: Account, amount: Int): Result<Transaction> {
    val tx = Transaction(nextTxId.getAndIncrement(), from, to, amount, Initial)
    return runCatching {
      process(tx)
    }.onFailure {
      executor.execute {
        var tx = tx
        while (tx.state != Done) {
          tx = process(tx)
        }
      }
    }
  }
}