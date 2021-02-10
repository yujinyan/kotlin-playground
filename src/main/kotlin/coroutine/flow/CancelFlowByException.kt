package coroutine.flow

import coroutine.log
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

suspend fun doWork() {
  delay(1000)
  throw CancellationException()
}

suspend fun main() {
  val flow = flow {
    emit(1)
    delay(1000)
    emit(2)
    emit(3)
    doWork()
  }

  coroutineScope {
    launch {
      flow.collect {
        log("collected $it")
      }
    }
  }
}