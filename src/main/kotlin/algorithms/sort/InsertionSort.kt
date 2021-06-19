package algorithms.sort

import algorithms.sort.quick.swap

// eg. 3,5,1,2,4
fun insertionSort(arr: IntArray) {
  for (i in 1..arr.lastIndex) {
    for (j in i downTo 1) {
      if (arr[j] < arr[j - 1]) {
        swap(arr, j, j - 1)
      } else break
    }
  }
}

fun main() {
  val array = intArrayOf(3, 1, 5, 2)
  insertionSort(array)
  println(array.asList())
}