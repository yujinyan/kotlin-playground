package coroutine.blogpost.structuredconcurrency

import kotlinx.coroutines.*
import kotlin.coroutines.coroutineContext

suspend fun sayHelloWorldInContext() {
  GlobalScope.launch(coroutineContext) {
    delay(500)
    print("Hello ")
  }
  GlobalScope.launch(coroutineContext) {
    delay(1000)
    print("World!")
  }
}

fun main() = runBlocking {
  launch {
    sayHelloWorldInContext()
  }.cancelAndJoin()
}

