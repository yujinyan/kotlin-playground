package coroutine

import kotlinx.coroutines.*
import java.lang.IllegalArgumentException

suspend fun doStuff() {
  delay(500)
  throw IllegalStateException("whoops")
}

suspend fun test1() {
  supervisorScope {
    launch {
      doStuff()
    }
  }
}

fun someFuncAsync(block: () -> Unit) {
  block()
}

fun test3() {
  try {
    someFuncAsync {
      throw IllegalArgumentException("whoops")
    }

  } catch (e: Throwable) {
    println("caught $e")
  }
}

suspend fun test2() {
  coroutineScope {
    launch {
      delay(1000)
      throw IllegalArgumentException("whoops")
    }

    launch {
      delay(2000)
      println("hi")
    }
  }
}

suspend fun test5() {
  val scope = CoroutineScope(Job())
  val shared = SupervisorJob()
  scope.launch(shared) {
    throw IllegalArgumentException("whoops")
  }
  scope.launch {
    delay(1000)
    println("hi")
  }
}

suspend fun main() {
  coroutineScope {
    test2()
  }
}