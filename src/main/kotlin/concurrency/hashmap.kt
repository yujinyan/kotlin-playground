package concurrency

import kotlinx.coroutines.coroutineScope
import kotlin.concurrent.thread

suspend fun main() {
  val map = HashMap<String, String?>()

//  coroutineScope {
//    repeat(10000) { i -> map[i.toString()] = 1.toString() }
//  }

  repeat(100) { i ->
    thread {
      map[i.toString()] = 1.toString()
    }
  }

  Thread.sleep(6000)

//  println(map.size)
  map.forEach { t, u -> println(t) }
}