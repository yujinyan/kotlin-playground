package coroutine.flow.toy

import kotlinx.coroutines.delay

typealias FlowCollector<T> = suspend (value: T) -> Unit

class FlowImpl2<T>(private val builder: suspend (collector: FlowCollector<T>) -> Unit) {

  suspend fun collect(collector: FlowCollector<T>) = builder(collector)

  fun <R> map(block: suspend (value: T) -> R) = FlowImpl2<R> { collector ->
    collect { value -> collector(block(value)) }
  }

  fun filter(block: suspend (value: T) -> Boolean) = FlowImpl2<T> { collector ->
    collect { value -> if (block(value)) collector(value) }
  }
}

suspend fun main() {
  FlowImpl2<Int> { emit ->
    emit(1)
    emit(2)
  }
    .map {
      println("$it: in map")
      it * it
    }
    .filter {
      println("$it: in filter")
      println("$it: delaying in filter...")
      delay(3000)
      it % 2 == 0
    }
    .collect {
      println("$it: in collect")
    }
}
