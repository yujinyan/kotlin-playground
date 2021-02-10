package coroutine

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.*

object ContinuationInterceptorDemo {
  suspend fun hello(): String {
//    delay(100)
    println("2. in suspend function")
    return "Hi"
  }
}

/**
 * ```
 * suspend fun main() {
 *   println(hello())
 * }
 * ```
 */
fun main() {
  ContinuationInterceptorDemo::hello.startCoroutine(Continuation(Printer) {
    println("4. result is $it")
  })
}

object Printer : AbstractCoroutineContextElement(ContinuationInterceptor), ContinuationInterceptor {
  override fun <T> interceptContinuation(continuation: Continuation<T>): Continuation<T> {
    val newCont = object : Continuation<T> {
      override val context: CoroutineContext = continuation.context

      override fun resumeWith(result: Result<T>) {
        println("resuming with $result")
        continuation.resumeWith(result)
      }
    }
    println("1. returning $newCont")
    return newCont
  }

  override fun releaseInterceptedContinuation(continuation: Continuation<*>) {
    super.releaseInterceptedContinuation(continuation)
    println("3. in release")
  }
}