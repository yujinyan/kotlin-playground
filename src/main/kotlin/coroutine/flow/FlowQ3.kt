package coroutine.flow

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
fun <T> Flow<T>.concurrentOnEach(
  scope: CoroutineScope,
  concurrency: Int = 10,
  block: suspend (T) -> Unit,
) = channelFlow {
  val chan = Channel<T>(capacity = concurrency)
  scope.launch {
    for (item in chan) block(item)
  }
  collect {
    chan.send(it)
    send(it)
  }
  chan.close()
}

suspend fun main() {
  coroutineScope {
    flow {
      println("only once")
      repeat(20) {
        emit(it)
      }
    }
      .concurrentOnEach(this) {
        println("concurrent onEach: $it")
      }
      .collect {
        delay(500)
        println("onEach $it")
      }
  }
}

