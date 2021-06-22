package coroutine.flow.toy

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext
import kotlin.coroutines.coroutineContext

suspend fun main() {
  FlowImpl<Int> {
    withContext(Dispatchers.IO) {
      emit(1)
    }
  }.collect {
    println(coroutineContext)
  }
}
