package rx

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableTransformer
import kotlin.random.Random

data class ApiResponse(val code: Int, val data: String)


fun generateResponse() = if (Random.nextBoolean())
  ApiResponse(0, "Hello") else ApiResponse(50000, "Hello")

val apiHandler = ObservableTransformer<ApiResponse, ApiResponse> { upstream ->
  upstream
    ?.flatMap {
      if (it.code != 0) Observable.error(IllegalArgumentException("Whoops"))
      else Observable.just(it)
    }
    ?.doOnError {
      println("caught error: $it")
    }
}

fun main() {
  Observable.create<ApiResponse> {
    it.onNext(generateResponse())
  }.compose(apiHandler)
    .doOnError { println("caught on ui: $it") }
    .retry(3)
    .onErrorComplete()
    .subscribe {
      println("success: received $it")
    }
}