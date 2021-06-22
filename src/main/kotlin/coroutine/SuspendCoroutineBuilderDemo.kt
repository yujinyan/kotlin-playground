package coroutine

import io.ktor.utils.io.*
import kotlin.concurrent.thread
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.time.ExperimentalTime
import kotlin.time.TimeSource

suspend fun await() = suspendCoroutine<Unit>{ cont ->
//  if (true) cont.resumeWith()
  thread {
    Thread.sleep(1000)
    cont.resume(Unit)
  }
}

@ExperimentalTime
suspend fun main() {
  mutableMapOf<Int, Int>().remove(1)
  val time = TimeSource.Monotonic.markNow()
  await()
  await()
  await()
  println("elapsed ${time.elapsedNow()}")
}