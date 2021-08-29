@file:Suppress("ConvertToStringTemplate")

package algorithms.leetcode.backtrack

import algorithms.TracingDeepRecursiveFunction
import algorithms.invoke

data class Param(
  val left: Int,
  val right: Int,
  val str: String,
) {
  override fun toString(): String {
    return "$left, $right, str=$str"
  }
}

suspend fun main() {
  val result = mutableListOf<String>()
  val generateParenthesis = TracingDeepRecursiveFunction<Param, Unit> { (left, right, str) ->
    if (left == 0 && right == 0) result += str
    if (left > 0) callRecursive(Param(left - 1, right + 1, str + '('))
    if (right > 0) callRecursive(Param(left, right - 1, str + ')'))
  }

  val param = Param(3, 0, "")
  generateParenthesis(param)
  println(result)
}

