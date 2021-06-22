
fun test(): Int {
  try {
    return 1
  } finally {
    println("hi")
    return 2
  }
}

fun main() {
  val result = test()
  println(result)
}