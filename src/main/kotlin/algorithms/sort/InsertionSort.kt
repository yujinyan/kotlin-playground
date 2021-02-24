package algorithms.sort

fun insertionSort(array: IntArray) {
  for (i in 1..array.lastIndex) {
    for (j in i downTo 1) {
      if (array[j - 1] < array[j]) break
      val temp = array[j - 1]
      array[j - 1] = array[j]
      array[j] = temp
    }
  }
}

fun main() {
  val array = intArrayOf(3, 1, 5, 2)
  insertionSort(array)
  println(array.asList())
}