package coroutine.blogpost.structuredconcurrency

import kotlinx.coroutines.*
import java.io.InputStream


fun CoroutineScope.process(stream: InputStream) {
  launch {
    delay(1000)
    stream.reader().readText()
  }
}

suspend fun main() {
  {}.javaClass.getResourceAsStream("/Hello.txt").use {
    coroutineScope {
      process(it)
    }
  }
}