package coroutine.blogpost.structuredconcurrency

import kotlinx.coroutines.*
import kotlin.coroutines.coroutineContext

suspend fun foo() {
  delay(1000)
  println("foo")
}

suspend fun bar() {
  delay(800)
  error("whoops")
  println("bar")
}

suspend fun composed() {
  GlobalScope.launch(coroutineContext) { foo() }
  GlobalScope.launch(coroutineContext) { bar() }
}


suspend fun main() {
  composed()
  println("after composed")
  while (true) {}
}