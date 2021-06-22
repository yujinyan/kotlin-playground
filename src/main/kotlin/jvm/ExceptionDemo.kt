package jvm

import java.lang.IllegalArgumentException

fun foo(): IllegalArgumentException {
  return bar()
}

fun bar() = IllegalArgumentException()

fun main() {
  val e = foo()
  e.printStackTrace()
}