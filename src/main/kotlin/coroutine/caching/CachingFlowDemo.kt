package coroutine.caching

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class Box(val value: Int)


suspend fun main() {

  val data = mutableListOf<Box>()

  val flow = flow {
    println("start emitting")
    emit(Box(1))
    emit(Box(2))
    emit(Box(3))
    println("end emitting")
  }.cacheIn(data)


  flow.collect {
    println("1: $it")
  }

  println("data is $data")

  flow.collect {
    println("2: $it")
  }
  println("data is $data")
}

private fun <T> Flow<T>.cacheIn(mutableList: MutableList<T>): Flow<T> = flow {
  if (mutableList.isNotEmpty()) {
    mutableList.forEach { emit(it) }
    return@flow
  }
  collect {
    mutableList.add(it)
    emit(it)
  }
}



