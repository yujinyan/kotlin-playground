@file:Suppress("DuplicatedCode")

package coroutine.flow

import coroutine.log
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

@FlowPreview
@ExperimentalCoroutinesApi
suspend fun main() {

  fun Flow<UiAction.LikeArticle>.handleLike(state: UiState): Flow<UiState> = transform {
    emit(state.apply { loading = true })
    log("call like api, position: ${it.position}")
    delay(500)
    emit(state.apply { likedPosition = it.position; loading = false })
  }

  fun Flow<UiAction.SearchQuery>.handleSearch(state: UiState): Flow<UiState> = transform {
    emit(state.apply { loading = true })
    log("call search api, query: ${it.value}")
    delay(200)
    emit(state.apply { query = it.value; loading = false })
  }

  val refresh = MutableSharedFlow<UiAction.Refresh>()
  val like = MutableSharedFlow<UiAction.LikeArticle>()
  val search = MutableSharedFlow<UiAction.SearchQuery>()

  coroutineScope {
    val actions: Flow<UiAction> = merge(refresh, like, search)

    launch { // simulate user actions
      delay(500); like.emit(UiAction.LikeArticle(1))
      delay(500); search.emit(UiAction.SearchQuery("foo"))
      delay(500); like.emit(UiAction.LikeArticle(2))
      delay(500); search.emit(UiAction.SearchQuery("bar"))
    }

    val state: Flow<UiState> = flow {
      var last = UiState().also { emit(it) }

      val merged = merge(
        actions.filterIsInstance<UiAction.SearchQuery>().handleSearch(last),
        actions.filterIsInstance<UiAction.LikeArticle>().handleLike(last)
      ).onEach { last = it }

      emitAll(merged)
    }

    state.collect {
      log("💡----------------------------$it")
    }
  }
}