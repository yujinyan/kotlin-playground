package coroutine.flow

import coroutine.log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

suspend fun main() {
  val state = MutableStateFlow(1)

  val flow = flow {
    delay(1000); emit("a")
    delay(1000); emit("b")
  }

  GlobalScope.launch {
    delay(5000)
    state.emit(2)
  }

  flow.combine(state) { i1, _ ->
    i1
  }.collect {
    log("collected flow: $it, state: ${state.value}")
  }
}