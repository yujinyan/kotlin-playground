package coroutine.flow

import coroutine.log
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch

suspend fun main() {
  val flow = MutableSharedFlow<Unit>(1)
  coroutineScope {
    launch {
      delay(500)
      flow.emit(Unit)
    }

    launch {
      flow.first()
      log("1: collect done")
    }

    launch {
      delay(1000)
      flow.first()
      log("2: collect done")
    }
    listOf(1, 2, 3).subList(0, 5)
  }
}

