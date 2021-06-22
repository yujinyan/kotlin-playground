package algorithms.sort.quick

fun swap(arr: IntArray, i: Int, j: Int) {
  val temp = arr[j]
  arr[j] = arr[i]
  arr[i] = temp
}

fun myHoarePartition(arr: IntArray, lo: Int, hi: Int): Int {
  val pv = arr[lo]
  var i = lo + 1
  var j = hi
  while (true) {
    while (arr[i] < pv) {
      i++
      if (i == hi) break
    }
    while (arr[j] > pv) {
      j--
      if (j == lo) break
    }
    if (j <= i) break
    swap(arr, i, j)
  }

  swap(arr, lo, j)
  return j
}

fun doWhileHoarePartition(arr: IntArray, lo: Int, hi: Int): Int {
  var i = lo
  var j = hi + 1
  val pv = arr[lo]
  while (true) {
    do i++ while (i <= hi && arr[i] < pv)
    do j-- while (j >= lo && arr[j] > pv)
    if (j < i) break
    swap(arr, i, j)
  }
  swap(arr, lo, j)
  return j
}

fun quickSort(
  arr: IntArray,
  partition: (arr: IntArray, lo: Int, hi: Int) -> Int
) {
  fun sort(lo: Int, hi: Int) {
    if (lo < hi) {
      val p = partition(arr, lo, hi)
      sort(lo, p - 1)
      sort(p + 1, hi)
    }
  }
  sort(0, arr.lastIndex)
}

fun quickSort(partition: (arr: IntArray, lo: Int, hi: Int) -> Int): (IntArray) -> Unit {
  return { arr: IntArray -> quickSort(arr, partition) }
}

fun main() {
//  intArrayOf(1, 5, 2, 3, 4)
//    .apply { quickSort(::myHoarePartition)(this) }
//    .also { println(it.toList()) }
//
//  intArrayOf(1, 5, 5, 3, 4)
//    .apply { quickSort(::myHoarePartition)(this) }
//    .also { println(it.toList()) }

  intArrayOf(1, 5, 2, 3, 4)
    .apply { quickSort(::doWhileHoarePartition)(this) }
    .also { println(it.toList()) }

  intArrayOf(3, 3, 3, 3, 3)
    .apply { quickSort(::doWhileHoarePartition)(this) }
    .also { println(it.toList()) }

  // stuck...
  intArrayOf(3, 3, 3, 3, 3)
    .apply { quickSort(::myHoarePartition)(this) }
    .also { println(it.toList()) }
}