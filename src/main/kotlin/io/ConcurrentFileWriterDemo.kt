package io

import java.io.File
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.thread

fun main() {
  val o = {}
  val lock = ReentrantLock()
  (0..9).map { i ->
    thread {
      lock.lock()
      File("out.txt").apply {
        repeat(20) {
          appendText(i.toString())
        }
      }
      lock.unlock()
//      File("out.txt").printWriter().use { writer ->
//        repeat(20) {
//          writer.print(i)
//        }
//      }
    }
  }.forEach {
    it.join()
  }

}