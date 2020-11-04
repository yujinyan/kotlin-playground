package coroutine

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread

 fun main() = runBlocking{
//  flowOf(1, 2, 3).map { it * 2 }.filter { it % 2 == 0 }

  val flow = MutableStateFlow(1)

  launch {
    flow.collect {
      println(it)

    }
  }

  launch {
    delay(1000)
    flow.value = 10
  }

  launch {
    flow.collect {
      println(it)
    }
  }


  thread(isDaemon = true) {
    println("hi")
  }

   Unit
}