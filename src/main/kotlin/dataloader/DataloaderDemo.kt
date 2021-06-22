package dataloader

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.concurrent.ConcurrentHashMap

//interface BatchLoader<ID, T> {
//  suspend fun load(ids: List<ID>)
//}

class BatchLoader<ID, T>(
  block: suspend (id: List<ID>) -> List<T>,
) {
  suspend fun CoroutineScope.load(id: ID): T {
    TODO()

  }
}

data class User(val id: Int, val invitedBy: Int)

class BatchLoader2<ID, T>(
  val scope: CoroutineScope,
  val block: suspend (id: List<ID>) -> List<T>,
) {
  private val cache = ConcurrentHashMap<ID, T>()

  private val ids = mutableSetOf<ID>()

  private val ids2Index = mutableMapOf<ID, Int>()

  private val result = Channel<List<T>>()


  @Synchronized
  fun load(id: ID): Deferred<T?> {
    ids2Index[id] = ids.size
    ids.add(id)

    return scope.async {
      val items = result.receive()
      val index = checkNotNull(ids2Index[id])
      items[index]
    }
  }

  suspend fun dispatchAndJoin() {
    result.send(block(ids.toList()))
    result.close()
  }
}

suspend fun main() {
//  val loader = BatchLoader<Int, User> {
//    delay(1000)
//    listOf(User(1, 3), User(2, 4))
//  }

  coroutineScope {
    val loader = BatchLoader2<Int, User>(this) {
      delay(1000)
      listOf(User(1, 3), User(2, 4))
    }

    loader.load(1).also { println(it.await()) }
    loader.load(2).also { println(it.await()) }
    loader.dispatchAndJoin()
  }
}