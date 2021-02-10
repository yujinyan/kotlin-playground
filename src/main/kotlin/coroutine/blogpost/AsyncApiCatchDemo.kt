package coroutine.blogpost

import kotlin.concurrent.thread

object AsyncApiCatchDemo {
  fun foo() {
    thread {
      error("whoops")
    }
//    Thread {
//      println("thread is ${Thread.currentThread().name}")
//      error("whoops")
//    }.start()
  }
}

fun main() {
  try {
    AsyncApiCatchDemo.foo()
  } catch (e: Throwable) {
    println(e)
  }
}