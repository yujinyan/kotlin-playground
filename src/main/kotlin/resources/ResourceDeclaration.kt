package resources

val hello = {}.javaClass.getResourceAsStream("/Hello.txt").reader().readText()

fun printHello() = println(hello)

fun main() {
  println(hello)
}