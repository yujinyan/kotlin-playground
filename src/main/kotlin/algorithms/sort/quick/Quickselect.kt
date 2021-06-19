package algorithms.sort.quick

fun quickSelect(arr: IntArray, k: Int, partition: (arr: IntArray, i: Int, j: Int) -> Int): Int {
  var lo = 0
  var hi = arr.lastIndex
  while (lo < hi) {
    val p = partition(arr, lo, hi)
    if (p < k) {
      // lo - p - k - hi
      lo = p + 1
    } else if (p > k) {
      // lo - k - p - hi
      hi = p - 1

    } else break
  }
  return arr[k]
}

fun quickSelectRange(arr: IntArray, k: Int, partition: (arr: IntArray, i: Int, j: Int) -> Int): List<Int> {
  var lo = 0
  var hi = arr.lastIndex
  while (lo < hi) {
    val p = partition(arr, lo, hi)
    if (p < k) {
      // lo - p - k - hi
      lo = p + 1
    } else if (p > k) {
      // lo - k - p - hi
      hi = p - 1

    } else break
  }
  return arr.slice(0..k)
}

fun main() {
//  quickSelect(intArrayOf(1, 5, 2, 3, 4), 3, ::myHoarePartition)
//    .also { println(it) }
//
//  quickSelectRange(intArrayOf(1, 5, 2, 3, 4), 3, ::myHoarePartition)
//    .also { println(it) }

  quickSelectRange(intArrayOf(5, 5, 5, 5, 5), 3, ::myHoarePartition)
    .also { println(it) }

  quickSelect(intArrayOf(1, 4, 3, 2, 5), 2, ::doWhileHoarePartition)
    .also { println(it) }
}