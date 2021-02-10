package coroutine.blogpost

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@ExperimentalCoroutinesApi
suspend fun work(done: ReceiveChannel<Unit>) {
  while (true) {
    if (done.isClosedForReceive) break
    println("working...")
    delay(1000)
  }
}

@ExperimentalCoroutinesApi
fun main() = runBlocking{
  val c = Channel<Unit>()

  launch {
    work(c)
  }

  delay(5000)
  c.close()

  Unit
}