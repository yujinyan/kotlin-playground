package coroutine

import kotlinx.coroutines.*

suspend fun main() {

  coroutineScope {
    val c = CompletableDeferred<Int>(coroutineContext[Job])
    launch {
      delay(1000)
      c.complete(1)
    }

    launch {
      val result = c.await()
      println("got $result")
      println("got ${c.await()}")
    }
  }
}