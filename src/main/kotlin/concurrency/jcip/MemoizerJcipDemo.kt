package concurrency.jcip

import java.util.concurrent.CancellationException
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.Future
import java.util.concurrent.FutureTask

fun interface Computable<A, V> {
  fun compute(arg: A): V
}

/**
 * Reference: JCIP Section 5.6
 */
class Memoizer<A, V>(
  private val c: Computable<A, V>,
) : Computable<A, V> {

  private val cache = ConcurrentHashMap<A, Future<V>>()

  override fun compute(arg: A): V {
    while (true) {
      var f: Future<V>? = cache[arg]
      if (f == null) {
        val ft = FutureTask { c.compute(arg) }
        f = cache.putIfAbsent(arg, ft)
        if (f == null) {
          f = ft
          ft.run()
        }
      }
      try {
        return f.get()
      } catch (e: CancellationException) {
        cache.remove(arg, f)
      }
    }
  }
}