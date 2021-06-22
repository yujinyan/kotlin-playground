package coroutine.actor

import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.yield
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

val testSeq = (1..1000).asSequence()

object ActorOverheadDemo {
  @ObsoleteCoroutinesApi
  suspend fun useActor() = coroutineScope {
    val actor = actor<Int> {
      val map = mutableSetOf<Int>()
      for (i in channel) map.add(i)
    }
     actor.offer(1)
    testSeq.forEach { actor.send(it) }
    actor.close()
  }

  suspend fun baseline() {
    val map = mutableSetOf<Int>()
    testSeq.forEach { map.add(it); yield() }
  }
}

@ObsoleteCoroutinesApi
@ExperimentalTime
fun main() = runBlocking {
  measureTime { ActorOverheadDemo.baseline() }.also { println(it) }
  measureTime { ActorOverheadDemo.useActor() }.also { println(it) }
  Unit
}