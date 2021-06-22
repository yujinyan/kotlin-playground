package coroutine.flow

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

sealed class Message

object Read : Message()
class Write(val value: Int) : Message()

@OptIn(ExperimentalCoroutinesApi::class)
val dataChannel = ConflatedBroadcastChannel(0)

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class, ObsoleteCoroutinesApi::class)
suspend fun main() {

  val f = flow {
    emitAll(dataChannel.asFlow())
  }

  coroutineScope {
    val actor = actor<Message> {
      for (msg in channel) {
        when (msg) {
          is Read -> Unit
          is Write -> {
            delay(500); dataChannel.send(msg.value)
          }
        }
      }
    }
    launch {
      delay(100)
      f.collect { println("collected $it") }
    }

    launch {
      delay(50)
      actor.send(Write(1))
    }
  }
}