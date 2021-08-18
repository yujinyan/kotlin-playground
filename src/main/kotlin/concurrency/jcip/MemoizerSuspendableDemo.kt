package concurrency.jcip

import kotlinx.coroutines.*
import java.util.concurrent.ConcurrentHashMap
import kotlin.time.ExperimentalTime
import kotlin.time.TimeSource

class SuspendableMemoizer<ID, V>(
  private val scope: CoroutineScope,
  private val computable: suspend (id: ID) -> V,
) {
  private val cache = ConcurrentHashMap<ID, Deferred<V>>()
  suspend fun calculate(id: ID): V = cache.getOrPut(id) {
    scope.async(start = CoroutineStart.LAZY) {
      computable(id)
    }
  }.apply { start() }.await()
}

@OptIn(ExperimentalTime::class)
suspend fun main() {
  val start = TimeSource.Monotonic.markNow()

  coroutineScope {
    val memoizer = SuspendableMemoizer(this) { id: Int ->
      println("computing id: $id")
      delay(id.toLong())
    }
    launch {
      memoizer.calculate(500)
    }
    launch {
      memoizer.calculate(500)
    }
  }

  println("elapsed: ${start.elapsedNow()}")
}
