@file:Suppress("FunctionName")

package algorithms.leetcode

fun lengthOfLongestSubstring(s: String): Int {
  // [abc]abcbb
  // a[bca]bcbb
  // ab[cab]cbb
  // abc[abc]bb
  // abca[bcb]b

  // [abc]bbcbb
  // a[bcb]bcbb
  // ab[cbb]cbb
  // abc[bbc]bb
  // abcabcbb

  if (s.isEmpty()) return 0
  val set = mutableMapOf<Char, Int>()
  var l = 0
  var r = 0
  set[s[0]] = 1
  while (r + 1 < s.length) {
    val next = s[r + 1]
    if (set[next]?: 0 > 0) {
      set[next] = set[next]!! - 1
      l++
      r++
    } else {
      set[next] = (set[next] ?: 0) + 1
      r++
    }
  }
  return r - l + 1
}

fun lengthOfLongestSubstring_nick(s: String): Int {
  var lo = 0
  var hi = 0
  var max = 0
  val set = hashSetOf<Char>()
  while (hi < s.length) {
    if (s[hi] in set) {
      set -= s[lo]
      lo++
//      hi++
    } else {
      set += s[hi]
      hi++
      max = Math.max(set.size, max)
    }
  }

  return max
}

fun main() {
//  println(lengthOfLongestSubstring("abcabcbb"))

  println(lengthOfLongestSubstring_nick("aab"))
  // [a]ab
  // [aa]b
  // [a]ab
}