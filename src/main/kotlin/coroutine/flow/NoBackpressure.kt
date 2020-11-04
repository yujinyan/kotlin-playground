package coroutine.flow

import coroutine.log
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

suspend fun main() {
  val state = MutableStateFlow(0)
  coroutineScope {
    launch {
      while (true) {
        delay(1000)
        log("updating state")
        state.value += 1
      }
    }

    launch {
      state.collect {
        log("collecting $it")
        delay(20000)
      }
    }
  }
}
