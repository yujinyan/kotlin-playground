package coroutine.flow

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime


@OptIn(ObsoleteCoroutinesApi::class, ExperimentalCoroutinesApi::class, FlowPreview::class)
class FlowOverheadDemo(val scope: CoroutineScope) {
  val range = (1..1_000)

  val data: Map<Int, Int> = range.associateBy { it }
  suspend fun baseline() {
    for (i in range) {
      data[i]; yield()
    }
  }

  val _dataChannel = ConflatedBroadcastChannel(0)
  val flow = flow {
//     emit(1)
    emitAll(_dataChannel.asFlow())
  }

  suspend fun useFlow() {
    for (i in range) {
      flow.first()
    }
  }
}

@OptIn(ExperimentalTime::class)
suspend fun main() {
  coroutineScope {
    val demo = FlowOverheadDemo(this)
    measureTime { demo.baseline() }.also { println("baseline: $it") }
    measureTime { demo.useFlow() }.also { println("useFlow: $it") }
  }
}