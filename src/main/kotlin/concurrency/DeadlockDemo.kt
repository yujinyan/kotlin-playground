package concurrency

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex

suspend fun main() {
  val s1 = Mutex()
  val s2 = Mutex()

  repeat(5) {
    coroutineScope {
      launch {
        s1.lock()
        delay(100)
        println("doing stuff in s1")
        s2.lock()
        s1.unlock()
        s2.unlock()
      }

      launch {
        s2.lock()
        delay(100)
        println("doing stuff in s2")
        s1.lock()
        s1.unlock()
        s2.unlock()
      }
    }
  }
}