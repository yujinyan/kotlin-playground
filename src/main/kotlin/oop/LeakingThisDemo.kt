package oop

class Foo {
  init {
    printBar(this)
  }
  val bar = "Bar"
}

fun printBar(foo: Foo) {
  println(foo.bar)
}

fun main() {
  Foo()
}
