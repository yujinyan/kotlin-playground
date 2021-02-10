package types

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import types.SomeDialog.Companion.newInstanceAsync

class SomeDialog {
  companion object {
    fun CoroutineScope.newInstanceAsync() = async {
      SomeDialog()
    }
  }
}

suspend fun main() {
  val result = GlobalScope.newInstanceAsync()
  result.await()
}