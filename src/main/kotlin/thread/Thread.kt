package thread

import kotlin.concurrent.thread

fun main() {
//  thread(isDaemon = true) {
//
//    Thread.sleep(1000)
//    println("hi")
//  }
//
  val thread = Thread {
    println("hi")
  }
  thread.start()


  thread.join()

}