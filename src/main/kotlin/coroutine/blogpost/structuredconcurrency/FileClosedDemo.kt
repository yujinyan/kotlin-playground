package coroutine.blogpost.structuredconcurrency

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import java.io.InputStream

fun process(stream: InputStream) {
  GlobalScope.launch {
    delay(1000)
    stream.reader().readText()
  }
}

fun demo() {
  File("./hello.txt").inputStream().use {
    process(it)
  }
}

fun main() {
  {}.javaClass.getResourceAsStream("/Hello.txt").use {
    process(it)
  }

  while (true) {
  }

}