package coroutine

import org.litote.kmongo.fields


val normalLambda: (value: Int) -> Int = { 1 }

val normalHigherLambda: () -> (value: Int) -> Int = { { 1 } }

//val suspendingLambda: suspend (value: Int) -> Int = suspend { v -> 1 }

val suspendingLambdaNoArg: suspend () -> Int = suspend { 1 }

val suspendingHigherLambdaNoArg: suspend () -> suspend () -> Unit = suspend { suspend { } }

class Test(val p: Int) {

}

fun interface MyInterface {
  fun hi()
}

interface MyInterface2: MyInterface {
  fun bonjour()
  fun greeting()
}

class Test3 {
  var myInterface: MyInterface? = null
    set(value) {
      field = value
    }

  var myInterfaceJava: MyInterfaceJava? = null
    set(value) {
      field = value
    }

  fun setListener(value: MyInterfaceJava) {

  }

  fun setListenerKt(value: MyInterface) {

  }
}

fun main() {

  val t = Test(1)
  t.p

  val t2 = Test2()
  t2.p

//  var a: MyInterface se

  val t3 = Test3().apply {
    myInterface = object: MyInterface2 {
      override fun bonjour() {
        TODO("Not yet implemented")
      }

      override fun greeting() {
        TODO("Not yet implemented")
      }

      override fun hi() {
        TODO("Not yet implemented")
      }

    }
  }

  Test3().setListenerKt {

  }

  Test3().setListener {

  }
}



