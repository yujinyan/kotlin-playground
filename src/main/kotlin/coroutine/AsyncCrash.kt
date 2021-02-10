package coroutine

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay

suspend fun test(): Int {
  delay(1000)
  log("test")
  return 1
//  throw IllegalStateException("whoops in test")
}

suspend fun main() {
//  val result = GlobalScope.async { test() }
//
//  result.await()

  val deferred = coroutineScope {
    async {
      test()
    }.also {
      log("in deferred end")
    }
  }

  val value = deferred.await()

  log("received $value")


//  try {
//    deferred.await()
//  } catch (e: Throwable) {
//    log("caught $e")
//  }


//    try {
//      val result = async { test() }
//    } catch (e: Throwable) {
//      log("caught $e")
//    }

//  delay(2000)
//  try {
//    result.await()
//  } catch (e: Throwable) {
//    log("caught $e")
//  }
}
