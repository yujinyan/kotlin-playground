package concurrency

import java.util.concurrent.CompletableFuture
import kotlin.time.ExperimentalTime
import kotlin.time.TimeSource


@OptIn(ExperimentalTime::class)
fun main() {
  val now = TimeSource.Monotonic.markNow()
  val data1 = CompletableFuture.supplyAsync {
    Thread.sleep(500);
    1
  }

  val data2 = CompletableFuture.supplyAsync {
    Thread.sleep(1000)
    2
  }

  val result = CompletableFuture.anyOf(data1, data2).get() as Int

  println("got $result, elapsed ${now.elapsedNow()}")
}