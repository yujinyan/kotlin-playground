package io

import java.io.File


fun main() {
  val file = File("C:\\Users\\yujinyan\\code\\study\\java\\Adder.class")
    .readBytes()

  val seq = file.iterator().asSequence()
//  seq.
//  seq.take(4).toList().also { println(it) }
  seq.take(2).toList().also { println(it) }

//  file.take(4).also { println(it) }
//  file.take(2).also { println(it) }
}