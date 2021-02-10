import kotlin.random.Random

val a = listOf(1, 2, 3)
val b = listOf(2, 3, 4)

println(a + b)
println(a - b)


List(5) { 0 }


listOf(1, 2, 3).count { it % 2 == 0 }

listOf(1, 2, 3, 4, 5).take(2)

"Hello World!".take(5)

data class Test(val a: String, val b: String)

val t = Test("Hello", "World")

val result = with(t) {
  when {
    a.isEmpty() -> 1
    else -> null
  }
}

listOf(1, 2, 3).any { it % 2 == 0 }

listOf(1, 3).find { it % 2 == 0 }


a.size.coerceAtLeast(10)

class User

fun toUserModel(value: String) = User()

fun save(user: User) = Unit
fun toast(msg: String) = Unit

"Peter"
  .let(::toUserModel)
  .let(::save)



fun getErrorMsg(): String? = if (Random.nextBoolean()) "whoops" else null

fun doPublish() {
  val msg = getErrorMsg()
  if (msg != null) {
    toast(msg)
    return // I could forget to `return` here.
  }
  // do other stuff
}

"abcde".removePrefix("mn")

buildString {
  append("hi")
}




