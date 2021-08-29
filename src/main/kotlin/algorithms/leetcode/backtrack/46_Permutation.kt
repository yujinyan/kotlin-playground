package algorithms.leetcode.backtrack

import algorithms.TracingDeepRecursiveFunction
import algorithms.invoke

suspend fun main() {
  val arr = listOf(1, 2, 3)
  val result = mutableListOf<List<Int>>()
  val list = mutableListOf<Int>()

  val permute = TracingDeepRecursiveFunction<List<Int>, Unit> f@{
    if (list.size == arr.size) {
      result += list.toList()
      return@f
    }

    for (n in arr) {
      if (n in list) continue
      list += n
      callRecursive(list.toList()) // parameter only for tracing
      list.removeAt(list.lastIndex)
    }
  }

  permute(list)
  println(result)
}