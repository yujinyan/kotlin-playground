package coroutine.blogpost.structuredconcurrency

import kotlinx.coroutines.*

suspend fun main() = coroutineScope {
  val job =
    launch {
      launch {
        launch { delay(1000) }
        launch { delay(1000) }
      }
      launch {
        launch { delay(1000) }
        launch { delay(1000) }
      }
    }
  /**
   * After [Job] completes, child jobs are no longer visible in [Job.children].
   * So we [delay] a bit in order to observe the complete tree.
   *
   * I've also tried other alternatives:
   * * [yield] does not work.
   * * [launch] root with [Dispatchers.Unconfined] works.
   *
   * This example also shows that [Job.children] attribute
   * could be accessed concurrently.
   */
  delay(500)

  job.cancel()

  job.printAsTree()
}

fun Job.printAsTree(indent: Int = 0) {
  println("${" ".repeat(indent)}$this")
  children.forEach {
    it.printAsTree(indent + 2)
  }
}

