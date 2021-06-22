package coroutine.flow

import coroutine.log
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.takeWhile
import kotlinx.coroutines.launch

suspend fun main() {
  val loadMoreRequest = MutableSharedFlow<Unit>()
  coroutineScope {
    launch {
      repeat(20) {
        delay(100)
        loadMoreRequest.emit(Unit)
      }
    }

    launch {
      while (true) {
//        loadMoreRequest.collect {
//          log("received event")
//        }

        loadMoreRequest.take(1).collect {
          log("received event")
        }

        log("calling api")
        delay(1000)
      }
    }
  }
}