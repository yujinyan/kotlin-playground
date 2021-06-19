package algorithms.sort

fun IntArray.swap(i: Int, j: Int) {
  val temp = this[j]
  this[j] = this[i]
  this[i] = temp
}
