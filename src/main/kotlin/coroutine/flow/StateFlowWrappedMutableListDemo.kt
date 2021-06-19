package coroutine.flow

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

sealed class State {
  data class Success(val data: List<Int>)
  data class Error(val error: Throwable) : State()
}

suspend fun main() {
  val list = mutableListOf(0)
  val state = MutableStateFlow(State.Success(list))

  coroutineScope {
    launch {
      list.add(1)
      state.value = State.Success(list)
      delay(1000)
      list.add(2)
      state.value = State.Success(list)
      delay(1000)
    }

    state.collect {
      println(it)
    }
  }
}