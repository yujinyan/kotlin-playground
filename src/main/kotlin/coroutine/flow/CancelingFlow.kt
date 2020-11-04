package coroutine.flow

import coroutine.log
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow

@ExperimentalCoroutinesApi
suspend fun main() {
  fun searchResults(query: String) = flow {
    var page = 1
    while (true) {
      delay(500)
      emit("result for $query, page ${page++}")
    }
  }

  fun userSearchQuery() = flow {
    val interval = (1000..3000)
    var query = 1
    while (true) {
      delay(interval.random().toLong())
      emit("user query: ${query++}".also { log(it) })
    }
  }

  userSearchQuery()
    .flatMapLatest { searchResults(it) }
    .collect { log(it) }
}