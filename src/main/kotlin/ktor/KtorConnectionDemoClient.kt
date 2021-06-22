package ktor

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*


suspend fun main() {
  HttpClient(CIO).use {
    val result = it.get<String>("http://localhost:8080/")
    println(result)
  }
}