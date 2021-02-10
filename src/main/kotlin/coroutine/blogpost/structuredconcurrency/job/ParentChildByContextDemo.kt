package coroutine.blogpost.structuredconcurrency.job

import arrow.core.extensions.either.applicativeError.catch
import kotlinx.coroutines.*
import kotlin.coroutines.coroutineContext

suspend fun sayHelloWorldInContext(): String {
  val a = GlobalScope.async(coroutineContext) {
    delay(500); error("whoops")
    println("a running")
    "Hello "
  }
  val b = GlobalScope.async(coroutineContext) {
    try {
      delay(1000)
      println("b running")
      " World!"
    } catch (e: Throwable) {
      println(e)
    }
  }
  return a.await() + b.await()
}

fun main() = runBlocking {
  val job = launch {
    println(sayHelloWorldInContext())
  }
  delay(600)
  job.cancelAndJoin()
}

