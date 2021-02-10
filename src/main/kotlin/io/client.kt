package io

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.util.*
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis

@KtorExperimentalAPI
suspend fun main() {
  val client = HttpClient(CIO)

  val time = measureTimeMillis {
    coroutineScope {
      repeat(1000) {
        launch {
          val result = client.get<String>("http://localhost:8080/test?id=$it")
          println("${Thread.currentThread().name}: received res $result")
        }
      }
    }
  }

  println("spent $time")
}
