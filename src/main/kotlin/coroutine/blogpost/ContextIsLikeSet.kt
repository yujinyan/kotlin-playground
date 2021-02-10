package coroutine.blogpost

import kotlinx.coroutines.CoroutineName

fun main() {
  println(CoroutineName("foo") + CoroutineName("bar") == CoroutineName("bar"))
}