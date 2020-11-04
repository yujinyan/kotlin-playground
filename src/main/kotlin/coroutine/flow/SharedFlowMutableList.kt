package coroutine.flow

import coroutine.log
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

suspend fun main() {

  val state = MutableSharedFlow<MutableList<Int>>(replay = 1)

  val data = mutableListOf<Int>()

  coroutineScope {
    launch {
      while (true) {
        data.add(0)
        delay(1000)
        state.emit(data)
      }
    }

    state.collect {
      log("collected $it")
    }
  }
}