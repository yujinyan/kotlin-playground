package coroutine

fun getToken(block: (String) -> Unit) {

}

fun upload(token: String) {
  // upload...
}


fun main() {
  getToken { token ->
    upload(token)
  }
}
