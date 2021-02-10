package io

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query

interface TestService {

  @GET("/test")
  suspend fun testApi(@Query("id") id: Int)
}

suspend fun main() {

  val retrofit = Retrofit.Builder()
    .baseUrl("http://localhost:8080")
    .build()

  val service = retrofit.create<TestService>()

  coroutineScope {
    repeat(100) {
      launch {
        service.testApi(it)
        val result = ""
        println("${Thread.currentThread().name}: received res $result")
      }
    }
  }
}
