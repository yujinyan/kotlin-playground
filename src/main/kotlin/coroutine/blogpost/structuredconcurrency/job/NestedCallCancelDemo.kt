package coroutine.blogpost.structuredconcurrency.job

import kotlinx.coroutines.*
import kotlin.coroutines.coroutineContext
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

@ExperimentalTime
object NestedCallCancelDemo {
  suspend fun foo() {
    try {
      bar()
    } catch (e: CancellationException) {
      println("caught $e")
    }
  }

  suspend fun bar() {
    println(coroutineContext.isActive)
    delay(Duration.INFINITE)
  }
}

@ExperimentalTime
suspend fun main() {
  val job = GlobalScope.launch {
    NestedCallCancelDemo.foo()
  }

  job.cancelAndJoin()
}

