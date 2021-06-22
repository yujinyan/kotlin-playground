package coroutine.blogpost.structuredconcurrency.job

import coroutine.blogpost.structuredconcurrency.printAsTree
import io.ktor.util.*
import kotlinx.coroutines.*
import kotlin.time.ExperimentalTime

@ExperimentalTime
@InternalAPI
suspend fun main() = coroutineScope {
  val job = Job()

  launch(job) {
    delay(1000)
    println("foo")
  }
  launch(job) {
    delay(1500)
    println("bar")
  }

  Unit
}