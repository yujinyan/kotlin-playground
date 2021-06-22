package coroutine.flow

import coroutine.log
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

sealed class UiAction {
  object Refresh : UiAction()
  class LikeArticle(val position: Int) : UiAction()
  class SearchQuery(val value: String) : UiAction()
}

data class UiState(
  var likedPosition: Int? = null,
  var query: String? = null,
  var loading: Boolean = false
)

fun Flow<UiAction.LikeArticle>.handleLike(state: UiState) = transform {
  emit(state.apply { loading = true })
  log("call like api, position: ${it.position}")
  delay(500)
  emit(state.apply { likedPosition = it.position; loading = false })
}

fun Flow<UiAction.LikeArticle>.test1(): Flow<UiState> = transform {
  log("in test1 LikeArticleTransformer")
  emit(UiState().apply { likedPosition = it.position })
}

fun Flow<UiAction.SearchQuery>.handleSearch(state: UiState) = transform {
  emit(state.apply { loading = true })
  log("call search api, query: ${it.value}")
  delay(200)
  emit(state.apply { query = it.value; loading = false })
}

fun Flow<UiAction.SearchQuery>.test2() = transform {
  log("in test2 SearchQueryTransformer")
  emit(UiState().apply { query = it.value })
}

@ExperimentalCoroutinesApi
suspend fun main() {
  val refresh = MutableSharedFlow<UiAction.Refresh>()
  val like = MutableSharedFlow<UiAction.LikeArticle>()
  val search = MutableSharedFlow<UiAction.SearchQuery>()

  coroutineScope {
    launch {
      delay(500); like.emit(UiAction.LikeArticle(1))
      delay(500); search.emit(UiAction.SearchQuery("foo"))
      delay(500); like.emit(UiAction.LikeArticle(2))
      delay(500); search.emit(UiAction.SearchQuery("bar"))
    }

    val flow = merge(refresh, like, search).shareIn(GlobalScope, SharingStarted.Eagerly)


    val flow2 = flow.scan(UiState()) { state, action ->
      when (action) {
        is UiAction.Refresh -> state
        is UiAction.LikeArticle -> state.apply { likedPosition = action.position }
        is UiAction.SearchQuery -> state.apply { query = action.value }
      }
    }

    val flow3: Flow<UiState> = flow.flatMapLatest {
      log("in flatMapLatest: $it")
      merge(
        flow.map { log("1 here is $it"); it }.filterIsInstance<UiAction.LikeArticle>().test1(),
        flow.map { log("2 here is $it");it }.filterIsInstance<UiAction.LikeArticle>().map { UiState(query = "in map") },
        flow.map { log("3 here is $it");it }.filterIsInstance<UiAction.SearchQuery>().map { log("in map 3");it }
          .test2(),
//        flowOf(UiState(query = "static"))
      ).onEach {
        log("in onEach $it")
      }
    }


    val flow4 = merge(
      flow.filterIsInstance<UiAction.LikeArticle>().test1(),
      flow.filterIsInstance<UiAction.SearchQuery>().test2()
    )

    val flow5 = flow.transform {
      log("transforming $it")
      emitAll(merge(
        flow.filterIsInstance<UiAction.LikeArticle>().test1(),
        flow.filterIsInstance<UiAction.SearchQuery>().test2()
      ))
//      emitAll()
    }

    val flow6 = flow {
      var last = UiState().also { emit(it) }
      val merged = merge(
        flow.filterIsInstance<UiAction.LikeArticle>().handleLike(last),
        flow.filterIsInstance<UiAction.SearchQuery>().handleSearch(last)
      )
      merged.onEach { last = it }
      emitAll(merged)
    }

    log("starting to collect")

    flow6.collect {
      val a= "🥰".trimIndent()
      val b = "❤"
      log("💡----------------------------$it")
    }
  }
}
