package coroutine.flow

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

suspend fun main() {
  val f = flow { // BAD!!
    emit(1)
    val value = withContext(Dispatchers.IO) {
      2
    }
    emit(value)
  }

  f.collect {
    println(it)
  }
}