package algorithms

import kotlin.random.Random

class MarkovGenerator(private val k: Int, text: String) {
  private val ngramStore = mutableMapOf<String, MutableList<String>>().apply {
    val ngramsSequence = text.splitToSequence("")
      .filterNot { it.isBlank() }
      .windowed(k) { it.joinToString("") }
    ngramsSequence.windowed(2)
      .forEach {
        getOrPut(it[0]) { mutableListOf() }.add(it[1].last().toString())
      }
  }

  fun generate(length: Int): String = buildString {
    val keys = ngramStore.keys.toList()
    var cur = keys[Random.nextInt(0, ngramStore.size - 1)].also { append(it) }
    var size = 0
    while (size < length) {
      size += k
      cur = cur.drop(1) + ngramStore[cur]!!.random().also { append(it) }
    }
  }
}


