package coroutine

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

suspend fun foo() {

}

suspend fun main() {
  val context: CoroutineContext = Dispatchers.IO + CoroutineName("CoCo") + CoroutineName("hi")
  val name: CoroutineName? = context[CoroutineName]
  println(name)

  (Dispatchers.IO + Printer).also { println(it) }

//  foo()
//
//  delay(1000)
//
//  println(context)
//  coroutineScope {
//    launch(context) {
//      println(coroutineContext)
//    }
//  }
}