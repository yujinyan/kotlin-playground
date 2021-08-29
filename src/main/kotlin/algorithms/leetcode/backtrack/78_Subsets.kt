package algorithms.leetcode.backtrack

import algorithms.TracingDeepRecursiveFunction
import algorithms.invoke


suspend fun main() {
  val result = mutableListOf<List<Int>>()
  val arr = listOf(1, 2, 3)

  val list = mutableListOf<Int>()

  val subsets = TracingDeepRecursiveFunction<Int, Unit> f@{ i: Int ->
    if (i == arr.size) {
      result += list.toList()
      return@f
    }
    list += arr[i]
    callRecursive(i + 1)
    list.removeAt(list.lastIndex)
    callRecursive(i + 1)
  }

  subsets(0)
  println(result)
}


