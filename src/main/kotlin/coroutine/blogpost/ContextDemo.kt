package coroutine.blogpost

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


suspend fun main() {
  coroutineScope {
    // 在 Context 中添加 CoroutineName[Coco] 元素
    launch(CoroutineName("Coco")) {
      contextCheck()
    }
  }
}

// 调用链：foo->bar->baz
suspend fun contextCheck() = bar()
suspend fun bar() = baz()
suspend fun baz() {
  // 在调用链中获取 Context 中的元素
  println(coroutineContext[CoroutineName])

  val context = coroutineContext[Job]

  suspendCoroutine<Unit> {
    println(it.context === context)
    it.resume(Unit)
  }

  coroutineScope {
    launch {
      println(coroutineContext[CoroutineName])
    }
  }
}
