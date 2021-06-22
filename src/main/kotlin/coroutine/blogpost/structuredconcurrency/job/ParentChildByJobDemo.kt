package coroutine.blogpost.structuredconcurrency.job

import kotlinx.coroutines.*
import kotlin.coroutines.coroutineContext

suspend fun sayHelloWorld(): String {
  val job = Job()
  val task1 = GlobalScope.async(job) {
    delay(500)
    "Hello "
  }
  val task2 = GlobalScope.async(job) {
    delay(1000);
    "World!"
  }
  return task1.await() + task2.await()
}

fun main() = runBlocking {
  launch {
    println(sayHelloWorld())
  }
  println("hi")
}