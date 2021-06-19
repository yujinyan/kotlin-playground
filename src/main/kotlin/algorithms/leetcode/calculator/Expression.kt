package algorithms.leetcode.calculator

import java.util.*

sealed class Expression {
  abstract fun interpret(): Int
}

class Number(
  private val n: Int,
) : Expression() {
  override fun interpret() = n
}

class Plus(
  private val lhs: Expression,
  private val rhs: Expression,
) : Expression() {
  override fun interpret(): Int = lhs.interpret() + rhs.interpret()
}

class Minus(
  private val lhs: Expression,
  private val rhs: Expression,
) : Expression() {
  override fun interpret(): Int = lhs.interpret() - rhs.interpret()
}

class Multiply(
  private val lhs: Expression,
  private val rhs: Expression,
) : Expression() {
  override fun interpret(): Int = lhs.interpret() * rhs.interpret()
}

class Divide(
  private val lhs: Expression,
  private val rhs: Expression,
) : Expression() {
  override fun interpret(): Int = lhs.interpret() / rhs.interpret()
}

fun evalRPN(tokens: Array<String>): Int {
  val operands = Stack<Int>()
  for (s in tokens) {
    val i = s.toIntOrNull()
    if (i != null) {
      operands.push(i)
      continue
    }

    with(operands) {
      when (s) {
        "+" -> push(pop() + pop())
        "-" -> push(pop().let { pop() - it })
        "*" -> push(pop() * pop())
        "/" -> push(pop().let { pop() / it })
        else -> TODO()
      }
    }
  }
  return operands.pop()
}






