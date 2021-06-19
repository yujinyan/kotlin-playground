package algorithms.sort

fun quickSort(arr: IntArray) {
  fun swap(i: Int, j: Int) {
    val temp = arr[j]
    arr[j] = arr[i]
    arr[i] = temp
  }

  fun partition(lo: Int, hi: Int): Int {
    var i: Int = lo
    var j: Int = hi + 1
    val pivotValue = arr[lo]
    while (true) {
      while (arr[++i] < pivotValue) if (i == hi) break
      while (arr[--j] > pivotValue) if (j == lo) break
      if (i >= j) break
      swap(i, j)
    }
    swap(lo, j)
    return j
  }

  fun sort(lo: Int, hi: Int) {
    if (hi <= lo) return
    val pivot = partition(lo, hi)
    sort(lo, pivot - 1)
    sort(pivot + 1, hi)
  }

  sort(0, arr.lastIndex)
}

fun main() {
  val arr = intArrayOf(5, 1, 4, 2, 3)
  quickSort(arr)
  println(arr.toList())
}