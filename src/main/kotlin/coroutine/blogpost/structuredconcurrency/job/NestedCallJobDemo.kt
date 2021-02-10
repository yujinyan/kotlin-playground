package coroutine.blogpost.structuredconcurrency.job

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

suspend fun main() =
  GlobalScope.launch {
    foo()
  }.join()

suspend fun foo() =
  bar()

suspend fun bar() {
  println(coroutineContext.isActive)
  delay(1000)
}

