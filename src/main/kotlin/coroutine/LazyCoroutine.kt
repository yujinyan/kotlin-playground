package coroutine

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlin.coroutines.CoroutineContext


suspend fun main() {
  log("starting...")

  val jobs = MutableSharedFlow<Job>(replay = 5)

//  val jobs = Channel<Job>(capacity = 1)

  coroutineScope {

    repeat(5) {
      val job: Job = launch(start = CoroutineStart.LAZY, context = coroutineContext) {
        log("job $it started")
        delay(2000)
        log("job $it end")
      }
      jobs.emit(job)
    }

    log("prepare to collect")
    jobs.collect {
      log("collected $it")
      it.start()
    }
  }
}