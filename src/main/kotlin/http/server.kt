package http

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

val store = HashMap<String, String?>()
fun processFunc(id: String) = "func param id is ${id}, store id is ${store["id"]}"

fun main() {
  embeddedServer(Netty, 8081) {
    routing {
      get("/") {
        val id: String = call.request.queryParameters["id"]!!
        store["id"] = id
        call.respondText(processFunc(id))
      }
    }
  }.start(wait = true)
}