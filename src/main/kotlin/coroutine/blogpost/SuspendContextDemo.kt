package coroutine.blogpost

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

suspend fun main() {

  suspend fun foo(context: CoroutineContext) {
    println(coroutineContext)
    println(context)
    Dispatchers.Default
//    withContext()
//    println(coroutineContext == context)
  }

  coroutineScope {
    val context = Dispatchers.IO
    launch(context) {
      foo(context)
    }
  }

}

