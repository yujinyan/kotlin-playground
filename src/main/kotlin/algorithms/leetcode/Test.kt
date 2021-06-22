package algorithms.leetcode

fun maxDistance(nums1: IntArray, nums2: IntArray): Int {
  var max = 0

  for (i in nums1.indices) {
    val target = nums1[i]

    var lo = i
    var hi = nums2.lastIndex

    while (lo <= hi) {
      val mi = lo + (hi - lo) / 2
      if (nums2[mi] < target) {
        hi = mi - 1
      } else if (nums2[mi] > target) {
        lo = mi + 1
      } else {
        lo++
      }
    }

    max = Math.max(max, lo)
  }

  return max
}

fun main() {
  maxDistance(intArrayOf(55,30,5,4,2), intArrayOf(100,20,10,10,5)).also { println(it) }
}

