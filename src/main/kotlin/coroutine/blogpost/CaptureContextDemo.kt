package coroutine.blogpost

import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

suspend fun main() {
  println(checkCallerContext(coroutineContext)) // true
  println(checkContinuationContext()) // true
}

suspend fun checkCallerContext(callerContext: CoroutineContext): Boolean =
  // 不更新 context 的情况下和调用方的 context 相同
  callerContext === coroutineContext

suspend fun checkContinuationContext(): Boolean {
  // suspendCoroutine 是连接 suspend 和回调的桥梁。
  // 传给它的 lambda 属于桥回调的那一边，不是 suspend 的 block，
  // 所以没有 coroutineContext。因此我们在桥的 suspend 这一边的时候
  // 保存一下这个 suspend 的 context
  val currentContext = coroutineContext

  // 通过 suspendCoroutine 获取当前 Continuation
  return suspendCoroutine { cont ->
    val contContext = cont.context
    // 两个 context 是相同的
    val isTheSame = contContext === currentContext
    cont.resume(isTheSame)
  }
}
