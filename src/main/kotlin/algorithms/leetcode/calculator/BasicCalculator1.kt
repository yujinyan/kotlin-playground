package algorithms.leetcode.calculator

import java.util.*

fun calculate(s: String): Int {
  val exp: List<Any> = toRPN(s)
  val operands = Stack<Int>()
  with(operands) {
    for (c in exp) {
      if (c is Int) {
        push(c)
        continue
      }
      when (c) {
        '+' -> push(pop() + pop())
        '-' -> push(pop().let { pop() - it })
        '*' -> push(pop() * pop())
        '/' -> push(pop().let { pop() / it })
      }
    }
  }
  return operands.pop()
}

//val Char.weight get() = when(this) {
//  '+' -> 1
//  '-' -> 1
//  '(' -> 0
//  else -> TODO()
//}

fun toRPN(s: String): List<Any> {
  val ret = mutableListOf<Any>()
  val ops = Stack<Char>();

  var i = 0
  while (i < s.length) {
    val c = s[i]
    if (c.isDigit()) {
      var n = c - '0'
      while (++i < s.length && s[i].isDigit()) {
        n = 10 * n + (s[i] - '0')
      }
      ret += n
      continue
    }

    when {
      c == ' ' -> { }
      c == '(' -> ops.push(c)
      c == ')' -> {
        while (ops.peek() != '(') ret += ops.pop()
        ops.pop()
      }
      c.isDigit() -> ret += c
      else -> {
        while (!ops.isEmpty() && ops.peek().weight >= c.weight) {
          ret += ops.pop()
        }
        ops.push(c)
      }
    }

    i++
  }
  while (!ops.isEmpty()) ret += ops.pop()
  return ret
}

fun main() {
  calculate("2147483647").also { println(it) }
}