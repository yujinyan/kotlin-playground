package coroutine

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import java.util.concurrent.atomic.AtomicInteger
import kotlin.random.Random
import kotlin.time.ExperimentalTime
import kotlin.time.seconds

suspend fun fetchCredential(): String {
  delay(1000)
  return "xxx-xxx-xxx"
}

suspend fun uploadImage(token: String, imageId: Int): String {
  log("starting to upload image $imageId with token $token")
  delay(1000)
  if (Random.nextBoolean()) throw RuntimeException("whoops, image $imageId failed")
  return "https://example.com/$imageId"
}


val retryRequest = Channel<Unit>()

val failedCount = AtomicInteger(0)

suspend fun <T> attempt(block: suspend () -> T): T {
  while (true) {
    try {
      return block()
    } catch (e: Throwable) {
      log("caught exception $e")
      failedCount.incrementAndGet()
      retryRequest.receive()
    }
  }
}

@ExperimentalTime
suspend fun main() {
  val imagesToUpload = (1..5)

  // simulate user clicking retry button
  GlobalScope.launch {
    while (true) {
      delay(5.seconds)
      log("sending retry request")
      repeat(failedCount.getAndSet(0)) { retryRequest.send(Unit) }
    }
  }

  supervisorScope {
    val token = attempt {
      fetchCredential().also { log("fetched token $it") }
    }

//    val urls = imagesToUpload.map {
//      attempt { uploadImage(token, it) }
//    }

    val futures = imagesToUpload.map {
      async { attempt { uploadImage(token, it) } }
    }

    val urls =  futures.map { it.await() }
    log("finished uploading $urls")
  }
}