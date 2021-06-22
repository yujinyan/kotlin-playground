package coroutine.channel

import coroutine.log
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

suspend fun main() {
  val channel = Channel<Boolean>()

  suspend fun Channel<Boolean>.waitUntil(value: Boolean) {
    for (v in this) {
      if (v == value) break
    }
  }

  coroutineScope {
    launch {
      channel.waitUntil(true)
      log("received true")
    }

    launch {
      log("sending false")
      channel.send(false)
      delay(500)
      log("sending false")
      channel.send(false)
      delay(500)
      log("sending true")
      channel.send(true)
    }
  }

}