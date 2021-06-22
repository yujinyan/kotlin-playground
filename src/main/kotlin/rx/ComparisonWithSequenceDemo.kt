package rx.comparison

import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import java.util.concurrent.Phaser
import kotlin.time.ExperimentalTime
import kotlin.time.seconds

@ExperimentalTime
suspend fun uploadImage(url: String): String {
  delay(1.seconds); println(url)
  return "url"
}

@ExperimentalTime
fun withCoroutine() {
  sequenceOf("image-1", "image-2", "image-3")
    .map {
      return@map runBlocking { uploadImage(it) }
    }
    .filter {
      it.startsWith("abc")
    }
}

fun withAsyncCallback() {
  fun uploadImageAsync(url: String, onSuccess: (url: String) -> Unit) {
    val thread = Thread {
      Thread.sleep(1000)
    }
    thread.start()
    thread.join()
    onSuccess(url)
  }

  sequenceOf("image-1", "image-2", "image-3")
    .map {
      uploadImageAsync(it) {
        // ???
      }
    }
  // .filter
}

fun withJdkConcurrencyControl() {
  fun uploadImageAsync(url: String, onSuccess: (url: String) -> Unit) {
    val thread = Thread {
      Thread.sleep(2000)
    }
    thread.start()
    thread.join()
    onSuccess("http://$url")
  }

  val phaser = Phaser(0)
  sequenceOf("image-1", "image-2", "image-3")
    .onEach {
      phaser.register()
    }
    .map {
      println("starting to upload $it")
      var url: String? = null
      uploadImageAsync(it) {
        println("received $it")
        phaser.arriveAndDeregister()
        url = it
      }
      return@map url!!
    }
    .filter {
      it.endsWith("1")
    }
    .forEach {
      println("Got it $it")
    }
}

fun withRxJava() {
  fun uploadImage(image: String): Observable<String> = Observable.just("http://$image")

  Observable.just("image-1", "image-2", "image-3")
    .flatMap {
      uploadImage(it)
    }
    .filter {
      it.endsWith("1")
    }
    .subscribe {
      println(it)
    }
}


@ExperimentalTime
suspend fun withFlow() {
  flowOf("image-1", "image-2", "image-3")
    .map {
      uploadImage(it)
    }
    .filter { it.endsWith("1") }
    .collectLatest {
      println(it)
    }
}


@ExperimentalTime
fun main() {
  println("------------------------")
  withJdkConcurrencyControl()

  flow {
    emit(1)
  }
}