package coroutine.actor

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.actor


private sealed class Message {
  class Read(val key: String, val ack: CompletableDeferred<String?>) : Message()
  class Write(val key: String, val value: String, val ack: CompletableDeferred<Unit>) : Message()
}

class SimpleDataStore(coroutineScope: CoroutineScope) {

  @OptIn(ObsoleteCoroutinesApi::class)
  private val actor = coroutineScope.actor<Message> { // this: ActorScope<Message>
    // state encapsulated inside the actor
    val cache = mutableMapOf<String, String>()

    // looping over messages one by one
    for (msg in channel) {
      when (msg) {
        is Message.Read -> msg.ack.complete(cache[msg.key])
        is Message.Write -> {
          cache[msg.key] = msg.value;
          msg.ack.complete(Unit)
        }
      }
    }
  }

  suspend fun read(key: String): String? {
    val ack = CompletableDeferred<String?>()
    actor.send(Message.Read(key, ack))
    return ack.await()
  }

  suspend fun write(key: String, value: String) {
    val ack = CompletableDeferred<Unit>()
    actor.send(Message.Write(key, value, ack))
    return ack.await()
  }
}

suspend fun main() {
  coroutineScope {
    val ds = SimpleDataStore(this + Job())

    ds.write("foo", "bar")
    println(ds.read("foo"))
  }
}