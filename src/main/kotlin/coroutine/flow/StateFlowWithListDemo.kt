package coroutine.flow

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

suspend fun main() {
  val _state = MutableStateFlow(listOf(0))
  val flow: Flow<List<Int>> = _state
  coroutineScope {
    launch {
      delay(1000)
      _state.value = listOf(1)
      delay(1000)
      _state.value = listOf(2)
      delay(1000)
      _state.value = listOf(3)

     _state.value += 1
      _state.value -= 1
    }

    flow.collect {
      println(it)
    }
  }
}