package coroutine

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

fun main() = runBlocking {
  val job = GlobalScope.launch {
    delay(1000)
    throw CancellationException()
  }

  job.join()

  coroutineScope {
    launch {
      delay(1000)
      log("delayed 1000")
      throw CancellationException()
    }
  }

  coroutineScope {
    val f = flow {
      emit(1)
      delay(1000)
      emit(2)
      delay(1000)
      throw CancellationException()
    }
    coroutineScope {
      f.collect {
        log("collected $it")
      }
    }
  }

  Unit
}