package coroutine.blogpost.structuredconcurrency.job

import coroutine.blogpost.structuredconcurrency.printAsTree
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.INFINITE
import kotlin.time.ExperimentalTime

@ExperimentalTime
suspend fun main() = coroutineScope {
  val job =
    launch {
      launch {
        launch { delay(INFINITE) }
        launch { delay(INFINITE) }
      }
      launch {
        launch { delay(INFINITE) }
        launch { delay(INFINITE) }
      }
    }

  delay(500)
  job.printAsTree()
  job.cancel()
}

