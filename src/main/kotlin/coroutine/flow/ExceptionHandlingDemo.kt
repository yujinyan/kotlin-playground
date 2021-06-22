package coroutine.flow

import coroutine.log
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion

suspend fun main() {
  val flow = flow {
    emit(1)
    emit(2)
    emit(3)
    error("whoops")
    emit(4)
    emit(5)
  }
//    .catch {
//
//    }
    .onCompletion {
      log("e: $it")
    }
    .catch {}

  flow.collect {
    log("collected $it")
  }
}
