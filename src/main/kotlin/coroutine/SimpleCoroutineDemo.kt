package coroutine

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.createCoroutine

suspend fun main() {
//  suspend {
//
//  }.createCoroutine()
  coroutineScope {
    launch {
      println("hi")
      launch {
        println("world")
      }
    }
  }
}