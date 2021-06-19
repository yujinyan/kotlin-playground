package coroutine.flow

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

private object EndToken

@Suppress("LocalVariableName")
@OptIn(ExperimentalCoroutinesApi::class)
fun <T> Flow<T>.concurrently(
  concurrency: Int = 10,
  block: suspend Flow<T>.() -> Unit,
) = channelFlow {
  val _sharedFlow = MutableSharedFlow<Any>()

  @Suppress("UNCHECKED_CAST")
  val sharedFlow = _sharedFlow.takeWhile { it !is EndToken } as Flow<T>

  launch { sharedFlow.block() }
  launch { sharedFlow.collect { send(it) } }
  delay(100)
  collect { _sharedFlow.emit(it as Any) }
  _sharedFlow.emit(EndToken)
}

suspend fun main() {
  flow { println("only once"); repeat(20) { emit(it + 1) } }
    .concurrently {
      delay(500)
      filter { it % 2 == 0 }.onEach { println("onEach $it") }.count().also { println("concurrent even: $it") }
      filter { it % 2 != 0 }.onEach { println("onEach $it") }.count().also { println("concurrent odd: $it") }
    }
    .collect {
      delay(200)
      println("collecting $it")
    }
}


