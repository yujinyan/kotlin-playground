package coroutine

import io.ktor.client.utils.*
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.io.File
import kotlin.coroutines.*
import kotlin.coroutines.intrinsics.createCoroutineUnintercepted

fun run(block: suspend () -> Unit) {
//  (block as Function1

}

// https://github.com/arrow-kt/arrow-fx/tree/master/arrow-fx-coroutines
object ContinuationDemo {
  suspend fun test(): Int = 1
  suspend fun fail(): Int = TODO()

}


fun main() {
  val cont = ContinuationDemo::test.createCoroutine(Continuation(EmptyCoroutineContext, ::println))
  cont.resume(Unit)


  ContinuationDemo::fail.startCoroutine(Continuation(EmptyCoroutineContext, ::println))
}

