package coroutine.flow.toy

import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.collect


class FlowImpl<T>(
  private val builder: suspend FlowCollector<T>.() -> Unit
) : Flow<T> {

  @OptIn(InternalCoroutinesApi::class)
  override suspend fun collect(collector: FlowCollector<T>) = collector.builder()
}

fun <T, R> Flow<T>.map(block: suspend (value: T) -> R) = FlowImpl<R> {
  collect { emit(block(it)) }
}

fun <T> Flow<T>.filter(block: suspend (value: T) -> Boolean) = FlowImpl<T> {
  collect { if (block(it)) emit(it) }
}


suspend fun main() = FlowImpl<Int> { emit(1); emit(2) }
  .map { it * it }
  .filter {
    delay(1000)
    it % 2 == 0
  }
  .collect { println("collected $it") }

