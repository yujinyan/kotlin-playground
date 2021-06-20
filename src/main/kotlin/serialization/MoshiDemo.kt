package serialization

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import kotlin.system.measureTimeMillis

val moshi = Moshi.Builder().build()

@JsonClass(generateAdapter = true)
data class Post(val title: String, val body: String)

fun main() {
  val a = moshi.adapter(Post::class.java).toJson(Post("foo", "bar"))
    .also { println(it) }
//
//
//  moshi.adapter(Post::class.java)
//    .lenient()
//    .fromJson("abc")


  measureTimeMillis {
    repeat(1000) {
      runCatching {
        moshi.adapter(Post::class.java)
          .lenient()
          .fromJson("""
            {"title":"foo","body":"bar"}
          """.trimIndent())
      }
    }
  }.let { println("result: $it") }

  measureTimeMillis {
    repeat(1000) {
      moshi.adapter(Post::class.java)
        .lenient()
        .fromJson("""
            {"title":"foo","body":"bar"}
        """.trimIndent())
    }
  }.let { println("result: $it") }

}
