package coroutine

import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.jvm.functions.Function1

val s: suspend () -> Int = suspend {
  println("in suspend")
  1
}

fun main() {
  println(s::class.java.superclass.name)
  s::class.java.interfaces.forEach { println(it) }

  val f = s as Function1<Continuation<Unit>, Int>

  f(object : Continuation<Unit> {
    override val context: CoroutineContext = EmptyCoroutineContext
    override fun resumeWith(result: Result<Unit>): Unit = TODO()
  })
}