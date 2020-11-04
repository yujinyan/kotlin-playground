package coroutine.channel

import coroutine.log
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

suspend fun main() {
  val channel = Channel<Unit>()

  coroutineScope {
    launch {
      delay(1000)
      channel.send(Unit)
      delay(1000)
      channel.send(Unit)
    }

    launch {
      for (i in channel) {
        println(i)
      }
//      channel.receive()
//      log("received")
    }
  }

}
