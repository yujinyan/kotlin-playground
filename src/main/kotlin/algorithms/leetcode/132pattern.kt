package algorithms.leetcode

import java.util.*
import kotlin.math.max

fun find132pattern(nums: IntArray): Boolean {
  val stack: Deque<Int> = ArrayDeque()
  var three = Int.MIN_VALUE

  for (i in nums.indices.reversed()) {
    val one = nums[i]
    if (one < three) {
      println("result is ${listOf(one, stack.peek(), three)}")
      return true
    }
    while (!stack.isEmpty() && stack.peek() < one) {
      three = max(stack.pop(), three)
    }
    // We're outside the while loop, stack.peek() >= one
    stack.push(one)
  }

  return false
}

fun main() {
//  find132pattern(intArrayOf(1, 0, 1, -4, -3)).also { println(it) }
  find132pattern(intArrayOf(3, 1, 4, 2)).also { println(it) }
  find132pattern(intArrayOf(-1, 3, 2, 0)).also { println(it) }
}
