package coroutine.flow

import kotlinx.coroutines.delay

typealias FlowCollector<T> = suspend (value: T) -> Unit

//class FlowImpl<T>(private val builder: suspend FlowCollector<T>.() -> Unit) {
//
//  suspend fun collect(collector: FlowCollector<T>) = collector.builder()
//
//  fun <R> map(block: suspend (value: T) -> R) = FlowImpl<R> {
//    collect { this(block(it)) }
//  }
//
//  fun filter(block: suspend (value: T) -> Boolean) = FlowImpl<T> {
//    collect { if (block(it)) this(it) }
//  }
//}

suspend fun main() {
//  FlowImpl<Int> {
//    this(1)
//    this(2)
//  }
//    .map {
//      println("$it: in map")
//      it * it
//    }
//    .filter {
//      println("$it: in filter")
//      println("$it: delaying in filter...")
//      delay(3000)
//      it % 2 == 0
//    }
//    .collect {
//      println("$it: in collect")
//    }
}
