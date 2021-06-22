package coroutine

import kotlinx.coroutines.*
import kotlin.coroutines.coroutineContext

fun test1(scope: CoroutineScope) {
  scope.launch {
    delay(2000)
    throw IllegalStateException("whoops in test 1")
  }
}

fun test2(scope: CoroutineScope) {
  scope.launch {
    while (true) {
      delay(500)
      log("test2: I'm working")
    }
  }
}

suspend fun sayHi() {
  println(coroutineContext)
}

suspend fun doNotDoThis() {
  CoroutineScope(coroutineContext).launch {
    println("delaying")
    delay(1000)
    println("hello")
  }
}

suspend fun main() {
  coroutineScope {
    doNotDoThis()
  }
}
