package coroutine.flow

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.FlowCollector
import kotlin.time.ExperimentalTime
import kotlin.time.TimeSource

@OptIn(FlowPreview::class)
fun <T, U>Flow<T>.concurrentTransform(
  concurrency: Int,
  transform: suspend FlowCollector<U>.(value: T) -> Unit,
): Flow<U> = map {
  flow { transform(it) }
}.flattenMerge(concurrency)


suspend fun FlowCollector<Int>.second(i: Int) {
  delay((10 * i).toLong())
  emit(i * i)
}

@OptIn(ExperimentalTime::class)
suspend fun main() {
  val sourceFlow = flow {
    println("only once")
    repeat(20) {
      emit(it)
    }
  }

  val now1 = TimeSource.Monotonic.markNow()
  var count1 = 0
  sourceFlow
    .transform { second(it) }
    .collect {
      count1++
    }
  println("counter1: $count1, took ${now1.elapsedNow()}")


  val now2 = TimeSource.Monotonic.markNow()
  var count2 = 0
  sourceFlow
    .concurrentTransform<Int, Int>(5) {
      second(2)
      print(".")
    }
    .collect {
      count2++
    }
  println("counter2: $count2, took ${now2.elapsedNow()}")
}