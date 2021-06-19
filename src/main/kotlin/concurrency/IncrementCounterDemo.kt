package concurrency

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

suspend fun main()  {
  var counter = 0
  coroutineScope {
    repeat(10_000) {
      launch { counter++ }
    }
  }
  println("counter: $counter")
}
