package coroutine.caching

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch

suspend fun main() {
  val state = MutableStateFlow(0)
  GlobalScope.launch {
    while (true) {
      state.value += 1
      delay(1000)
    }
  }

  state
    .collect {
      println(it)
      println(state.replayCache)
    }
}