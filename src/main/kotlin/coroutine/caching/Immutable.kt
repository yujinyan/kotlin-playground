package coroutine.caching

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

suspend fun main() {
  val state = MutableStateFlow(listOf<Int>())

  GlobalScope.launch {
    delay(100)
    state.value += 1
    state.value += listOf(2, 3)
  }

  state.collect {
    println(it) // []
                // [1]
                // [1, 2, 3]

    val a = "Hello"
    val b = "$a World"
  }
}