package coroutine.blogpost.structuredconcurrency

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch




fun main() {
  writeData()
  // 🤔 数据写完了吗？可以读这个数据了吗？
}

fun writeData() {
  GlobalScope.launch(Dispatchers.IO) {
    // doing some work ..
    delay(1000)
    // write data
  }
}
