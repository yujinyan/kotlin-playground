package intellij


interface HasA {
  val a: String
}

interface HasB {
  val b: String
}

interface HasC {
  val c: String
}

interface HasD {
  val d: String
}

interface Composed : HasA, HasB, HasC, HasD {
  fun run() {
    val aa by lazy { a }
    val bb by lazy { b }
    val cc by lazy { c }
    val dd by lazy { d }
    println(aa + bb + cc + dd)
//    println(aa + bb + cc)
//    println(aa + cc + bb)
  }
}