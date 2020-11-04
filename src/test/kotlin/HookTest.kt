import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.html.*

fun main() {
  val consumer = TestConsumer()
  val (counter, setCount) = useState(0)
  val (switch, setSwitch) = useState(true)
  val (data, setData) = useState(listOf(1, 2, 3))

  HTML(mapOf(), consumer).apply {
    body {
      p(classes = "foo bar") { +"counter is $counter" }
      div {
        ul {
          for (i in data) li { +i.toString() }
        }
      }
      if (switch) p { +"Hello World!" }
    }
  }

  GlobalScope.launch {
    setCount(1)
  }
}


fun <T> useState(value: T): Pair<T, (T) -> Unit> {

  return value to { newValue: T -> Unit }
}