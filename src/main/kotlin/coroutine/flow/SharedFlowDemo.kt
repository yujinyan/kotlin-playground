package coroutine.flow

import coroutine.log
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

suspend fun main() {
  val state = MutableSharedFlow<Int>(replay = 1)

  coroutineScope {
    launch {
      delay(2000)
      var i = 0
      while (true) {
        val valueToEmit = (i++).also { log("preparing to emit $it") }
        state.emit(valueToEmit)
      }
    }

    launch {
      state.collect {
        log("received $it")
        log("cache is ${state.replayCache}")
        delay(1000)
      }
    }
  }
}