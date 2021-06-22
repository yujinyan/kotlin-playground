package algorithms.leetcode

import java.util.HashSet
import java.util.HashMap


fun numDifferentIntegers(word: String): Int {
  if (word.length == 1) {
    return if (Character.isDigit(word[0])) 1 else 0
  }
  val set: MutableSet<Int> = LinkedHashSet()
  var prev = word[0]
  var n = 0
  if (Character.isDigit(prev)) n = prev.toInt()
  for (i in 1 until word.length) {
    val c = word[i]
    if (Character.isDigit(c)) {
      n = 10 * n + (c - '0')
    } else {
      if (Character.isDigit(prev)) {
        set.add(n)
        n = 0
      }
    }
    prev = c
  }
  if (n != 0) set.add(n)
  return set.size
}

fun evaluate(s: String, knowledge: List<List<String>>): String? {
  val dict: MutableMap<String, String> = HashMap()
  val sb = StringBuilder()
  for (k in knowledge) {
    dict[k[0]] = k[1]
  }
  var i = 0
  while (i < s.length) {
    val c = s[i]
    if (c == '(') {
      for ((k, v) in dict) {
        var match = true
        for (j in 0 until k.length) {
          if (k[j] != s[i + j + 1]) {
            match = false
            break
          }
        }
        if (match) {
          sb.append(v)
          i = k.length + 2
        }
      }
    } else {
      sb.append(c)
      i++
    }
  }
  return sb.toString()
}

fun main() {
  println(evaluate("(name)is(age)yearsold", listOf(listOf("name", "bob"), listOf("age", "two"))))

}
