import coroutine.log
import kotlinx.coroutines.channels.Channel
import java.util.concurrent.atomic.AtomicInteger

fun <T> attemptFactory() = { block: suspend () -> T ->
  val retryRequest = Channel<Unit>()
  val failedCount = AtomicInteger(0)
  suspend {
    while (true) {
      try {
        block()
      } catch (e: Throwable) {
        log("caught exception $e")
        failedCount.incrementAndGet()
        retryRequest.receive()
      }
    }
  }
}


