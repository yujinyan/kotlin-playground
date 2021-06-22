package coroutine.blogpost

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.Continuation
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.CoroutineContext

fun main() {
  (CoroutineName("Coco") + Dispatchers.IO).also { it: CoroutineContext ->
    println(it[CoroutineName] == CoroutineName("Coco")) // true
    println(it[ContinuationInterceptor] == Dispatchers.IO) // true
  }

  (CoroutineName("foo") + CoroutineName("bar")).also { it: CoroutineContext ->
    println(it[CoroutineName] == CoroutineName("bar")) // true
  }
}