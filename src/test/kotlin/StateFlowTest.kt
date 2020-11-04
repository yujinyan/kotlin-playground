import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map

@ExperimentalCoroutinesApi
suspend fun main() {
    val counter = MutableStateFlow(0)
    val doubledCounter = counter.map { it * 2 }
    counter.value++
    counter.value++
    doubledCounter.collect {
        println(it)
    }

}