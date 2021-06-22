package coroutine.blogpost.structuredconcurrency

import kotlinx.coroutines.*
import kotlin.coroutines.coroutineContext

suspend fun sayHelloWorld() {
  val job = Job(parent = coroutineContext[Job])
  GlobalScope.launch(job) {
    delay(500)
    print("Hello ")
  }
  GlobalScope.launch(job) {
    delay(1000)
    print("World!")
  }
  job.complete()
  job.join()
}

fun main() = runBlocking {
  val job = launch {
    sayHelloWorld()
  }

  delay(100)
  job.cancelAndJoin()
  delay(2000)
}