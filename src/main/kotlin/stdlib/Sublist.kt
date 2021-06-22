package stdlib

fun main() {
  val oldList = mutableListOf(1, 2, 3)
  val subList = oldList.subList(0, 2)
  oldList[0] = 100
  println(subList)
}