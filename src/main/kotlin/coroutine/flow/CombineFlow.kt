package coroutine.flow

import coroutine.log
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.time.ExperimentalTime
import kotlin.time.seconds

@ExperimentalTime
suspend fun main() {
  suspend fun search(inputSearch: String, appliedSort: String): String {
    delay(1.seconds)
    return "search result for $inputSearch, sort: $appliedSort"

  }

  val inputSearch = MutableSharedFlow<String>(replay = 1)
  val appliedSort = MutableSharedFlow<String>(replay = 1)

  val searchResultFlow = combine(inputSearch, appliedSort) { search, sort ->
    search(search, sort)
  }

  coroutineScope {
    inputSearch.emit("q0")
    appliedSort.emit("0")

    log("launch 1")
    launch {
      delay(2.seconds); inputSearch.emit("q1")
      delay(2.seconds); inputSearch.emit("q2")
    }

    log("launch 2")
    launch {
      delay(6.seconds); appliedSort.emit("1")
      delay(2.seconds); appliedSort.emit("2")
    }

    log("launch 3")
    launch {
      searchResultFlow.collect { log("collected $it") }
    }
  }
}