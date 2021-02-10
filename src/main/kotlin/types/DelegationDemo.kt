package types

interface OnClickHandler {
  fun onClick()
}

class Handler(val r: Any): OnClickHandler {
  init {
    println((r as AnotherActivity).someField)
  }
  override fun onClick() {
  }
}




class SomeActivity: OnClickHandler by Handler(1) {

}

class AnotherActivity: OnClickHandler  {
  val handler = Handler(this)
  val someField = Any()
  override fun onClick() {
    TODO("Not yet implemented")
  }
}

fun main() {
  AnotherActivity()
}
