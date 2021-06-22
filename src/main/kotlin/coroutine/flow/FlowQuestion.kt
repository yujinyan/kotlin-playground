package coroutine.flow

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

@OptIn(ExperimentalCoroutinesApi::class)
fun <T, U> Flow<T>.concurrent(
  concurrency: Int,
  transform: suspend (T) -> U,
): Flow<U> = flow {

  coroutineScope {
    val channel = produce(capacity = concurrency) {
      collect { send(async { transform(it) }) }
    }
    for (el in channel) {
      emit(el.await())
    }
  }
}

suspend fun main() {
  val inputs = flow {
    println("will only print once")
    repeat(20) {
      emit(it)
    }
  }
  inputs // readInputs()
    .onEach {
      // extract...
    }
    .onEach {
      //
    }
    .transform {
      emit(it)
    }
    .transform {
      emit(it)
      emit(it)
    }
    .concurrent(5) {
      println("concurrently: $it")
      delay(1000)
    }
    .count() // or maintain in onEach
    .also { println("count: $it") }
}