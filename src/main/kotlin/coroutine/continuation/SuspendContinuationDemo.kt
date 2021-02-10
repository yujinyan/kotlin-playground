package coroutine.continuation

import kotlinx.coroutines.delay

//suspend fun foo() = bar()

suspend fun bar(): String {
  delay(100)
  return "bar"
}

suspend fun main() {
  val b = bar()
  println(b)
}