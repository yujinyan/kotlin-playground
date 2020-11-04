package coroutine.caching

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

private var n = 0
suspend fun someHeavyWeightCalculation(): Int {
  delay(1000)
  return (n++)
}

suspend fun main() {
  val state = MutableStateFlow<Int>(0)

  GlobalScope.launch {
    while (true) {
      state.value = someHeavyWeightCalculation().also { println("f calculated $it") }
      delay(1000)
    }
  }

  state.collect {
    println("consumer collected: $it")
    println("consumer sleeping a while")
    delay(2000)
  }
}