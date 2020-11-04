import kotlinx.html.*

fun template() {

}

fun main() {
  val consumer = TestConsumer()
  val data = listOf(1, 2, 3)
  var counter = 0
  var switch = true
  HTML(mapOf(), consumer).apply {
    body {
      p(classes = "foo bar") {
        +"counter is ${dynamic(counter)}"
      }
      div {
        ul {
          for (i in dynamic(data)) {
            li { +i.toString() }
          }
        }
      }
      if (dynamic(switch)) {
        p { +"Hello World!" }
      }
    }
  }
  counter++
  switch = false
}

fun <T : Any> HTMLTag.dynamic(value: T): T {
  (consumer as? TestConsumer)?.apply {
    registerDynamic(value)
  }

  return value
}