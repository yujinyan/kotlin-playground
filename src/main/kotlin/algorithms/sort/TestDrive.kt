package algorithms.sort


private val IntArray.isSorted: Boolean
  get() {
    for (i in 1..lastIndex) {
      if (get(i) <= get(i - 1)) return false
    }
    return true
  }

fun main() {
  (1..1000).shuffled().toIntArray()
    .let {
      insertionSort(it)
      require(it.isSorted) { "not sorted" }
    }
}
