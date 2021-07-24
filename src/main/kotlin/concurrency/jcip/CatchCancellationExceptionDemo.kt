package concurrency.jcip

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.random.Random
import kotlin.time.ExperimentalTime
import kotlin.time.TimeSource

suspend fun fetchData(): Int {
  delay(1000)
  if (Random.nextInt(0, 10) < 2)
    error("whoops")
  return 1
}

@OptIn(ExperimentalTime::class)
fun main() = runBlocking {
  val now = TimeSource.Monotonic.markNow()
  val job = launch {
    runCatching {
      fetchData()
    }

    // Pretend we continue to do other work...
    while (true) {
    }
  }

  delay(200)
  job.cancel()
  println(now.elapsedNow())
}
