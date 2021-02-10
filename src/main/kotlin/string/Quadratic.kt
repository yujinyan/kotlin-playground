package string

import kotlin.system.measureTimeMillis

fun subText(value: String): String {
  var len = 0.0
  return value.codePoints().toArray().fold("") { acc, c ->
    println(acc)
    len += (if (c > 256) 1.0 else 0.5)
    if (len > 15) {
      return@fold acc
    }
    acc + c.toChar()
  }
}

fun subText2(value: String): String {
  var len = 0.0

  return buildString {
    value.codePoints().takeWhile {
      len += (if (it > 256) 1.0 else 0.5)
      len < 15
    }.forEach {
      println(it.toChar())
      append(it.toChar())
    }
  }
}

private val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

fun randomString() = (1..10000)
  .map { i -> kotlin.random.Random.nextInt(0, charPool.size) }
  .map(charPool::get)
  .joinToString("");


fun main() {

  subText(randomString())

//  val texts = (1..1000).map { randomString() }
//
//  measureTimeMillis {
//    texts.map { subText2(it) }.let { println("result new is ${it.last()}") }
//  }.let { println("took $it") }
//
//  measureTimeMillis {
//    texts.map { subText(it) }.let { println("result old is ${it.last()}") }
//  }.let { println("took $it") }
}