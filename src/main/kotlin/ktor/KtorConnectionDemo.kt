package ktor

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield

fun main() {
  embeddedServer(Netty, port = 8080) {
    routing {
      get("/") {
        heavyLifting()
        call.respondText("hello world!")
      }
    }
  }.start(wait = true)
}

suspend fun heavyLifting() {
  println("starting to do heavy lifting")
  while (true) {
    delay(100)
    print(".")
    yield()
  }
}