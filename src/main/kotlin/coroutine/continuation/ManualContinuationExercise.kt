package coroutine.continuation

import kotlinx.coroutines.delay
import java.util.concurrent.CountDownLatch
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.intrinsics.COROUTINE_SUSPENDED
import kotlin.time.ExperimentalTime
import kotlin.time.TimeSource


/**
 * Simulates the following suspend function.
 * ```kotlin
 * suspend fun foo() {
 *   delay(1000)
 *   return 1
 * }
 *
 * suspend fun main() {
 *   println(foo())
 * }
 * ```
 */
@ExperimentalTime
fun main() {
  // Use a latch to keep JVM spinning.
  val latch = CountDownLatch(1)
  // Record the elapsed time.
  val timer = TimeSource.Monotonic.markNow()

  foo(object : Continuation<Any> {
    // CoroutineContext is irrelevant in this example.
    override val context: CoroutineContext = EmptyCoroutineContext

    override fun resumeWith(result: Result<Any>) {
      println(result.getOrThrow())
      latch.countDown()
    }
  })

  latch.await()
  println("elapsed: ${timer.elapsedNow()}")
}

fun foo(continuation: Continuation<Any>): Any {
  class FooContinuation : Continuation<Any> {
    var label: Int = 0
    override fun resumeWith(result: Result<Any>) {
      val outcome = invokeSuspend()
      if (outcome === COROUTINE_SUSPENDED) return
      continuation.resume(outcome)
    }

    fun invokeSuspend(): Any {
      return foo(this)
    }

    override val context: CoroutineContext = EmptyCoroutineContext
  }

  val cont = (continuation as? FooContinuation) ?: FooContinuation()
  return when (cont.label) {
    0 -> {
      val delay: suspend (Long) -> Unit = ::delay

      @Suppress("UNCHECKED_CAST")
      val df = delay as Function2<Long, Continuation<Any>, Any>
      cont.label++
      df(1000, cont)
      COROUTINE_SUSPENDED
    }
    1 -> 1 // return 1
    else -> error("shouldn't happen")
  }
}
