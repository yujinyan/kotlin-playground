package algorithms.sort

fun smallestK(arr: IntArray, k: Int): IntArray {

  fun swap(x: Int, y: Int) {
    val temp = arr[y]
    arr[y] = arr[x]
    arr[x] = temp
  }

  fun partition(lo: Int, hi: Int): Int {
    var i = lo + 1
    var j = hi
    val pv = arr[lo]

    while (true) {
       while (arr[i] < pv) {
         i++
         if (i == hi) break
       }

       while (arr[j] > pv) {
         j--
         if (j == 0) break
       }

      if (j <= i) break
      swap(i, j)
    }

    swap(lo, j)
    return j
  }


  var lo = 0
  var hi = arr.lastIndex

  while (lo < hi) {
    val p = partition(lo, hi)
    if (p > k) {
      hi = p - 1
    } else if (p < k) {
      lo = p + 1

    } else break

  }

  val ret = IntArray(k)

  for (i in 0 until k) {
    ret[i] = arr[i]
  }

  return ret
}

fun main() {
//  val arr = intArrayOf(1, 3, 5, 7, 2, 4, 6, 8)
//  println(smallestK(arr, 4).toList())

  intArrayOf(1, 5, 2, 3, 4)
    .let { smallestK(it, 4) }
    .also { println(it.toList()) }

}