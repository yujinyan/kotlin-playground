package coroutine

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.selects.select
import kotlin.time.ExperimentalTime
import kotlin.time.TimeSource

suspend fun data1(): Int {
  delay(500)
  return 1
}

suspend fun data2(): Int {
  delay(1000)
  return 2
}

/**
 * Race [Deferred] a la `Promise.race` in JavaScript.
 */
// https://github.com/Kotlin/kotlinx.coroutines/issues/424
@OptIn(ExperimentalTime::class)
suspend fun main() {
  val start = TimeSource.Monotonic.markNow()
  coroutineScope {
    val deferred: List<Deferred<Int>> = listOf(async { data1() }, async { data2() })

    val data = select<Int> {
      deferred.forEach { d -> d.onAwait { it } }
    }

    deferred.forEach { it.cancel() }
    println(data)
  }

  // A little more than 500ms.
  println("elapsed ${start.elapsedNow()}")
}