package coroutine.flow

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

val searchQuery = MutableSharedFlow<String>(1)

val staticSections = searchQuery.map {
  "static sections for $it"
}

suspend fun main() {
  coroutineScope {
    launch {
      searchQuery.emit("query 1")
    }

    launch {
      staticSections.collect {
        println(it)
      }
    }
  }
}
