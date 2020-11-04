import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.html.*
import org.w3c.dom.events.Event
import org.w3c.dom.html.HTMLElement
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.coroutineContext

class TestConsumer : TagConsumer<Unit> {
    override fun finalize() {
        TODO("Not yet implemented")
    }

    override fun onTagAttributeChange(tag: Tag, attribute: String, value: String?) {
        println("tag: $tag")
        println("attribute: $attribute")
        println("value: $value")
    }

    override fun onTagComment(content: CharSequence) {
        TODO("Not yet implemented")
    }

    override fun onTagContent(content: CharSequence) {
        println("content: $content")
    }

    override fun onTagContentEntity(entity: Entities) {
        TODO("Not yet implemented")
    }

    override fun onTagContentUnsafe(block: Unsafe.() -> Unit) {
        TODO("Not yet implemented")
    }

    override fun onTagEnd(tag: Tag) {
        println("tag end: $tag")
    }

    override fun onTagEvent(tag: Tag, event: String, value: (Event) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun onTagStart(tag: Tag) {
        println("<${tag.tagName}> start: $tag")
    }

    suspend fun observe(data: Flow<Any>) {
        data.collect {
            println("change observed: $it")
        }
    }

    fun registerDynamic(value: Any) {
        println("registering dynamic $value")
    }
}

@ExperimentalCoroutinesApi
class ObservableContent(content: Any) :
    CharSequence by content.toString(),
    MutableStateFlow<CharSequence> by MutableStateFlow(content.toString()) {
}

@ExperimentalCoroutinesApi
suspend fun main() {
    coroutineScope {
        val consumer = TestConsumer()
        val counter = MutableStateFlow(0)
        HTML(mapOf(), consumer).apply {
            body {
                p(classes = "foo bar") {
                    withReactive(counter, this@coroutineScope) { +"counter is $it" }
                }
            }
        }
        counter.value++
    }
//    while (true) {}
}


@ExperimentalCoroutinesApi
fun Tag.withReactive(content: StateFlow<Any>, scope: CoroutineScope = GlobalScope, block: (CharSequence) -> Unit) {
    (consumer as? TestConsumer).also {
//        println("do reactive stuff")
        scope.launch {
            it?.observe(content)
        }
    }
    block(content.value.toString())
}


