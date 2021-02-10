package coroutine.blogpost.structuredconcurrency.job

import kotlinx.coroutines.*

suspend fun main() {
  val job = GlobalScope.launch {
    printFibonacciSlowly(50)
  }
  delay(100)
  job.cancelAndJoin()
}

suspend fun printFibonacciSlowly(n: Int) {
  for (i in 0..n) {
    yield()
    println(fibonacci(i))
  }
}