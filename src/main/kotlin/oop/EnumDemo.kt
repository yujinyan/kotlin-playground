package oop

enum class Prefix(val value: String) {
  Post("post"), InviteCode("invite-code")
}

fun main() {
  println(Prefix.Post.name)
}
