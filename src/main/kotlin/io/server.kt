package io

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.coroutines.delay

fun main() {
  val server = embeddedServer(Netty, port = 8080) {
    routing {
      get("/test") {
        delay(1000)
        call.respondText("hi, ${call.request.queryParameters["id"]}")
      }
    }
  }
  server.start(wait = true)
}