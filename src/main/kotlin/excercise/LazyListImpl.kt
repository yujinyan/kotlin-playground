package excercise

//interface Sequence {
//  fun emit(value: Int)
//}

interface LazyList {
  fun collect(block: (Int) -> Unit)
}

class LazyListImpl(
  val list: List<Int>
) : LazyList {

  override fun collect(block: (Int) -> Unit) {
    list.forEach(block)
  }
}

fun LazyList.map(mapper: (Int) -> Int): LazyList {
  val self = this
  return object : LazyList {
    override fun collect(block: (Int) -> Unit) {
      self.collect {
        block(mapper(it))
      }
    }
  }
}

fun main() {
  val list = LazyListImpl(listOf(1, 2, 3, 4, 5))
  list
    .map { value ->
      (value * 2).also { println("mapped $it") }
    }
    .collect {
      println("collected $it")
    }
}