package http

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.util.*
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@KtorExperimentalAPI
val client = HttpClient(CIO)

@KtorExperimentalAPI
suspend fun main() = coroutineScope {
  repeat(100) { i ->
    launch {
      client.get<String>("http://localhost:8081?id=${i}").also {
        println(it)
      }
    }
  }
}

