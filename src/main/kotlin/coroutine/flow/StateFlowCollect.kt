package coroutine.flow

import coroutine.log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

suspend fun testSuspend(value: Int) {
  delay(1)
}

fun testNormal(value: Int) {

}

suspend fun hof() {
}


suspend fun main() {

  val a: suspend (value: Int) -> Unit = ::testSuspend
//  val b: suspend (value: Int) -> Unit = ::testNormal

//  val suspendingLambda: suspend () -> suspend (value: Int) -> Unit = suspend {
//    suspend {  value -> Int
//      delay(1000)
//    }
//  }


  fun onReceiveFlow(state: StateFlow<Int>) {
    state.onEach {
      println("onEach $it")
    }.launchIn(GlobalScope)
  }

  val state = MutableStateFlow(0)
  onReceiveFlow(state)

  GlobalScope.launch {
    delay(1000)
    state.value += 1

    delay(1000)
    state.value += 1

    delay(1000)
    state.value += 1
  }

  while (true) {
  }
//  coroutineScope {
//    launch {
//      delay(1000)
//      state.value += 1
//
//      delay(1000)
//      state.value += 1
//
//      delay(1000)
//      state.value += 1
//    }
//  }
}