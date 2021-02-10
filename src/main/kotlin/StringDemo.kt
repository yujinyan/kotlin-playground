
fun textLength(value: String) = value.codePoints().toArray().fold(0) { acc, c ->
  if (c > 256) acc + 2
  else (acc + 1)
}

fun main() {
//  println(textLength("你好"))
//  println(textLength("hello"))
  textLength("🎈🎆🎇a你好")
}