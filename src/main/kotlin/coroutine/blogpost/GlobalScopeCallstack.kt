package coroutine.blogpost

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object GlobalScopeCallstack {
  suspend fun foo() {
    println("called foo")
    bar()
  }

  suspend fun bar() {
    println("called bar")
    GlobalScope.launch {
      baz()
    }
  }

  suspend fun baz() {
    error("whoops")
  }
}

suspend fun main() {
  GlobalScopeCallstack.foo()
  while (true) {
  }
}