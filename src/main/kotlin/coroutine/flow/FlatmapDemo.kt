package coroutine.flow

import coroutine.log
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

@ExperimentalCoroutinesApi
suspend fun main() {
  val f = flowOf(1, 2, 3).flatMapLatest {
    flow {
      emit(it * it)
      emit(it * it * it)
    }
  }

  f.collect {
    log("collected $it")
  }
}
