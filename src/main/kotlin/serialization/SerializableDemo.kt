import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.plus
import java.io.File
import java.io.ObjectOutputStream
import java.io.Serializable

data class Value<T>(val value: T) : Serializable

data class Person(val name: String, val gender: String = "abc") : Serializable {
  companion object {
    @JvmStatic private val serialVersionUID = 1381488341778288004
  }
}

suspend fun main() {
  Channel<Int> {  }
  GlobalScope + Dispatchers.IO
  val p = Person("Peter", "M")

  val fs = File("test.bin").outputStream()
  ObjectOutputStream(fs).use {
    it.writeObject(p)
  }
}