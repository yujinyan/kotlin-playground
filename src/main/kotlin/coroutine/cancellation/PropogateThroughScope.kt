package coroutine.cancellation

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.supervisorScope

suspend fun main() {
//  coroutineScope {
//    launch {
//      throw IllegalStateException("1")
////      throw CancellationException("1")
//    }
//  }
//
//  coroutineScope {
//    throw CancellationException("2")
//  }

  supervisorScope {
      throw CancellationException()
  }

  println("end")

}