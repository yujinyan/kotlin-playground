package coroutine

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

fun getFlow() = flow {
  var n = 0
  while (true) {
    emit(n++)
    delay(1000)
  }
}

suspend fun main() {
  val state = MutableStateFlow(0)

  val job = GlobalScope.launch {
    getFlow().collect {
      state.value = it
    }
  }

  GlobalScope.launch {
    state.collect {
      println("consumer collected: $it")
    }
  }

  GlobalScope.launch {
    delay(5000)
    job.cancel()

    state.value = 100
  }.join()
}