package coroutine.flow

import coroutine.log
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.time.ExperimentalTime
import kotlin.time.seconds

@ExperimentalTime
suspend fun main() {
  suspend fun callSomeApi(): String {
    delay(1.seconds)
    return "hello world".also { log("calculated result: $it") }
  }
  coroutineScope {
    val flow = flow {
      emit(callSomeApi())
    }.shareIn(this, SharingStarted.Lazily)


    repeat(5) {
      launch {
        flow.collect {
          log("collected $it")
        }
      }
    }
  }
}