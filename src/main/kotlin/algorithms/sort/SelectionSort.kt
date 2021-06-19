package algorithms.sort

fun selectionSort(arr: IntArray) {
  for (i in 0..arr.lastIndex) {
    var min = i
    for (j in i + 1..arr.lastIndex) {
      if (arr[j] < arr[min]) min = j
    }
    arr.swap(i, min)
  }
}

fun main() {
  val array = intArrayOf(3, 1, 5, 2)
  selectionSort(array)
  println(array.asList())
}
