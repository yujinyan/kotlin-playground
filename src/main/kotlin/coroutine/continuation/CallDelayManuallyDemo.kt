package coroutine.continuation

import kotlinx.coroutines.delay
import java.util.concurrent.CountDownLatch
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.time.ExperimentalTime
import kotlin.time.TimeSource

@ExperimentalTime
fun main() {
  val delay: suspend (Long) -> Unit = ::delay

  @Suppress("UNCHECKED_CAST")
  val df = delay as Function2<Long, Continuation<Unit>, Any>

  val now = TimeSource.Monotonic.markNow()
  val latch = CountDownLatch(1)

  df(1000, object : Continuation<Unit> {
    override val context: CoroutineContext = EmptyCoroutineContext

    override fun resumeWith(result: Result<Unit>) {
      println("resumed: $result, elapsed: ${now.elapsedNow()}")
      latch.countDown()
    }
  })

  latch.await()
}
