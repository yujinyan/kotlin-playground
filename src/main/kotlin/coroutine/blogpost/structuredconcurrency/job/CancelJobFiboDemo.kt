package coroutine.blogpost.structuredconcurrency.job

import kotlinx.coroutines.*

suspend fun main() {
  val job = GlobalScope.launch(Dispatchers.IO) {
    for (i in 0..50) {
      // 除了 return 之外还可以抛 `CancellationException`
      // 协程库提供的 `ensureActive` 封装了这一方法
      // 另外也可以使用 `yield`
      // -----下面这些写法都可以-----
      // if (!isActive) throw CancellationException()
      // ensureActive()
      // yield()
      if (!isActive) return@launch
      println(fibonacci(i))
    }
  }
  delay(100)
  // 取消 job 并等待，避免 jvm 直接退出
  job.cancelAndJoin()
}

// deliberately slow fibonacci
fun fibonacci(n: Int): Int = if (n <= 1)
  n else fibonacci(n - 1) + fibonacci(n - 2)
