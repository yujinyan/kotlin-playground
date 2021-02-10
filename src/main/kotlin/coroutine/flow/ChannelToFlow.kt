package coroutine.flow

import coroutine.log
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

suspend fun main() {
  val refreshRequest = MutableStateFlow(0)
  val search = flow {
    delay(1000); emit("foo")
    delay(1000); emit("bar")
  }

  coroutineScope {
    launch {
      delay(5000)
      delay(1000); refreshRequest.value += 1
      delay(1000); refreshRequest.value += 1
    }

    search.combine(refreshRequest) { query, _ ->
      delay(500)
      "search result for $query"
    }.collect {
      log("collected $it")
    }
  }
}
