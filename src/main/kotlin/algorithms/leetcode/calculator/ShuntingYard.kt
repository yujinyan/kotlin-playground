package algorithms.leetcode.calculator

import java.lang.StringBuilder
import java.util.*


val Char.weight
  get() = when (this) {
    '+' -> 1
    '-' -> 1
    '*' -> 2
    '/' -> 2
    '(' -> 0
    else -> TODO("unrecognized [$this]")
  }

fun shuntingYard(string: String): String {
  val ret = StringBuilder()
  val operators = Stack<Char>()

  for (c in string.toCharArray()) {
    if (Character.isLetter(c)) {
      ret.append(c)
    } else {
      while (
        !operators.isEmpty()
        && operators.peek().weight >= c.weight
      ) ret.append(operators.pop())

      operators.push(c)
    }
  }

  while (!operators.isEmpty()) ret.append(operators.pop())

  return ret.toString()
}

fun shuntingYardWithBrackets(string: String): String {
  val ret = StringBuilder()
  val ops = Stack<Char>()

  for (c in string.toCharArray()) {
    when {
      Character.isLetter(c) -> ret.append(c)
      c == '(' -> ops.push(c)
      c == ')' -> {
        while (ops.peek() != '(') ret.append(ops.pop())
        ops.pop()
      }
      else -> {
        while (!ops.isEmpty() && ops.peek().weight >= c.weight) {
          ret.append(ops.pop())
        }
        ops.push(c)
      }
    }
  }

  while (!ops.isEmpty()) ret.append(ops.pop())

  return ret.toString()
}

fun main() {
  shuntingYard("a+b*c-d").also { println(it) } // 123*+4-
  shuntingYardWithBrackets("a+(b+c)+d").also { println(it) }
  shuntingYardWithBrackets("a+(m+n*o-p)+d").also { println(it) }
}