package algorithms

import kotlin.coroutines.*
import kotlin.coroutines.intrinsics.COROUTINE_SUSPENDED
import kotlin.coroutines.intrinsics.suspendCoroutineUninterceptedOrReturn

class TracingDeepRecursiveFunction<T, R>(
  internal val block: suspend DeepRecursiveScope<T, R>.(T) -> R,
)

operator fun <T, R> TracingDeepRecursiveFunction<T, R>.invoke(value: T): R =
  DeepRecursiveScopeImpl<T, R>(block, value).runCallLoop()

sealed class DeepRecursiveScope<T, R> {
  /**
   * Makes recursive call to this [TracingDeepRecursiveFunction] function putting the call activation frame on the heap,
   * as opposed to the actual call stack that is used by a regular recursive call.
   */
  public abstract suspend fun callRecursive(value: T): R

  /**
   * Makes call to the specified [TracingDeepRecursiveFunction] function putting the call activation frame on the heap,
   * as opposed to the actual call stack that is used by a regular call.
   */
  public abstract suspend fun <U, S> TracingDeepRecursiveFunction<U, S>.callRecursive(value: U): S

  @Deprecated(
    level = DeprecationLevel.ERROR,
    message =
    "'invoke' should not be called from DeepRecursiveScope. " +
        "Use 'callRecursive' to do recursion in the heap instead of the call stack.",
    replaceWith = ReplaceWith("this.callRecursive(value)")
  )
  @Suppress("UNUSED_PARAMETER")
  public operator fun TracingDeepRecursiveFunction<*, *>.invoke(value: Any?): Nothing =
    throw UnsupportedOperationException("Should not be called from DeepRecursiveScope")
}

// ================== Implementation ==================

private typealias DeepRecursiveFunctionBlock = suspend DeepRecursiveScope<*, *>.(Any?) -> Any?

private val UNDEFINED_RESULT = Result.success(COROUTINE_SUSPENDED)

@Suppress("UNCHECKED_CAST")
private class DeepRecursiveScopeImpl<T, R>(
  block: suspend DeepRecursiveScope<T, R>.(T) -> R,
  value: T,
) : DeepRecursiveScope<T, R>(), Continuation<R> {
  // Active function block
  private var function: DeepRecursiveFunctionBlock = block as DeepRecursiveFunctionBlock

  // Value to call function with
  private var value: Any? = value

  // Continuation of the current call
  private var cont: Continuation<Any?>? = this as Continuation<Any?>

  // Completion result (completion of the whole call stack)
  private var result: Result<Any?> = UNDEFINED_RESULT

  override val context: CoroutineContext
    get() = EmptyCoroutineContext

  override fun resumeWith(result: Result<R>) {
    this.cont = null
    this.result = result
  }

  private var level = 1
  private val contIndents = mutableMapOf<Any, Int>()
  private val valueIndents = mutableListOf(value to 0)

  override suspend fun callRecursive(value: T): R = suspendCoroutineUninterceptedOrReturn { cont ->
    // calling the same function that is currently active
    if (cont in contIndents) {
      val n = contIndents[cont]!!
      valueIndents += value to n
      level = n + 1
    } else {
      val n = level++
      contIndents[cont] = n
      valueIndents += value to n
    }

    this.cont = (cont as Continuation<Any?>)
    this.value = value
    COROUTINE_SUSPENDED
  }

  override suspend fun <U, S> TracingDeepRecursiveFunction<U, S>.callRecursive(value: U): S =
    suspendCoroutineUninterceptedOrReturn { cont ->
      // calling another recursive function
      val function = block as DeepRecursiveFunctionBlock
      with(this@DeepRecursiveScopeImpl) {
        val currentFunction = this.function
        if (function !== currentFunction) {
          // calling a different function -- create a trampoline to restore function ref
          this.function = function
          this.cont = crossFunctionCompletion(currentFunction, cont as Continuation<Any?>)
        } else {
          // calling the same function -- direct
          this.cont = cont as Continuation<Any?>
        }
        this.value = value
      }
      COROUTINE_SUSPENDED
    }

  private fun crossFunctionCompletion(
    currentFunction: DeepRecursiveFunctionBlock,
    cont: Continuation<Any?>,
  ): Continuation<Any?> = Continuation(EmptyCoroutineContext) {
    this.function = currentFunction
    // When going back from a trampoline we cannot just call cont.resume (stack usage!)
    // We delegate the cont.resumeWith(it) call to runCallLoop
    this.cont = cont
    this.result = it
  }

  private fun printTree2() {
    valueIndents.forEach { (value, indent) ->
      repeat(indent * 4) { print(" ") }
      println(value)
    }
  }


  @Suppress("UNCHECKED_CAST")
  fun runCallLoop(): R {
    while (true) {
      // Note: cont is set to null in DeepRecursiveScopeImpl.resumeWith when the whole computation completes
      val result = this.result
      val cont = this.cont
        ?: return (result as Result<R>).getOrThrow().also {
          printTree2()
        } // done -- final result

      // The order of comparison is important here for that case of rogue class with broken equals
      if (UNDEFINED_RESULT == result) {
        // call "function" with "value" using "cont" as completion
        val r = try {
          // This is block.startCoroutine(this, value, cont)
          (function as Function3<Any?, Any?, Continuation<R>, Any?>)(this, value, cont)
        } catch (e: Throwable) {
          cont.resumeWithException(e)
          continue
        }
        // If the function returns without suspension -- calls its continuation immediately
        if (r !== COROUTINE_SUSPENDED)
          cont.resume(r as R)

      } else {
        // we returned from a crossFunctionCompletion trampoline -- call resume here
        this.result = UNDEFINED_RESULT // reset result back
        cont.resumeWith(result)
      }
    }
  }
}
