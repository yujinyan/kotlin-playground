package coroutine.caching

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

suspend fun main() {
  val state = MutableStateFlow(mutableListOf<Int>())

  GlobalScope.launch {
    delay(100)
    state.value = state.value.apply { add(1) }
  }
  state.collect {
    println(it) // []
  }
}