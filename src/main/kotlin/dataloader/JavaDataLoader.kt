package dataloader

import org.dataloader.BatchLoader
import org.dataloader.DataLoader
import java.util.concurrent.CompletableFuture


fun main() {
  val batchLoader = BatchLoader<Int, User> {
    CompletableFuture.supplyAsync {
      listOf(User(1, 3), User(2, 4))
    }
  }

  val userLoader = DataLoader.newDataLoader(batchLoader)

  val user1 = userLoader.load(1).thenAccept { println(it) }
  val user2 = userLoader.load(2).thenAccept { println(it) }

  userLoader.dispatchAndJoin()
}