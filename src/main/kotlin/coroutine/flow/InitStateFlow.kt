package coroutine.flow

import coroutine.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class Client {
  fun CoroutineScope.publish(): StateFlow<Int> {
    val state = MutableStateFlow(1)
    launch {
//    delay(1000)
      state.value = 2
      delay(1000)
      state.value = 3
    }
    return state
  }

  suspend fun publish2(flow: MutableStateFlow<Int>) {

  }
}

suspend fun main() {
  val client = Client()
  coroutineScope {
    val flow = with(client) { publish() }
    flow.collect {
      log("collected $it")
    }
    log("asdc")
  }
}