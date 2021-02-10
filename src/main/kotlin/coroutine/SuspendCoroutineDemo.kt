package coroutine

import kotlinx.coroutines.runBlocking
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

object SuspendCoroutineDemo {
  fun fetchData(block: (Int) -> Unit) {
    block(1)
  }

  suspend fun fetchData(): Int = suspendCoroutine { cont ->
    fetchData {
      cont.resume(it)
    }
  }
}

fun main() {
  // uses callback
  SuspendCoroutineDemo.fetchData {
    println(it)
  }

  // uses suspend function
  runBlocking {
    val data = SuspendCoroutineDemo.fetchData()
    println(data)
  }
}