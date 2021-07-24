package concurrency.jcip

import java.util.concurrent.FutureTask
import java.util.concurrent.locks.AbstractQueuedSynchronizer
import kotlin.concurrent.thread

/**
 * Reference
 * - JCIP, p.184
 * - FutureTask before jdk8 https://github.com/openjdk-mirror/jdk7u-jdk/blob/master/src/share/classes/java/util/concurrent/FutureTask.java
 */
class ValueLatch<T> {
  private class Sync<T> : AbstractQueuedSynchronizer() {
    private var result: T? = null

    override fun tryAcquireShared(arg: Int): Int {
      return if (state == 1) 1 else -1
    }

    override fun tryReleaseShared(arg: Int): Boolean {
      state = 1
      return true
    }

    fun innerGet(): T {
      acquireSharedInterruptibly(0)
      return result!!
    }

    fun innerSet(value: T) {
      result = value
      while (true) {
        if (state == 1) return
        if (compareAndSetState(0, 1)) {
          releaseShared(0)
          result = value
        }
      }
    }

    fun isSet(): Boolean {
      return state == 1
    }
  }

  private val sync = Sync<T>()

  fun isSet(): Boolean {
    return sync.isSet()
  }

  fun setValue(value: T) {
    sync.innerSet(value)
  }

  fun getValue(): T {
    return sync.innerGet()
  }
}

fun main() {
  val latch = ValueLatch<Int>()

  thread {
    Thread.sleep(500)
    latch.setValue(10)
  }

  repeat(10) {
    thread {
      println("value is ${latch.getValue()}")
    }
  }
}