package serialization

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class ApiResponse<T>(
  @Suppress("SpellCheckingInspection")
  val errcode: Int,
  val msg: String,
  val data: T,
)

@Serializable
sealed class Section

@Serializable
@SerialName("shop")
data class ShopSection(val name: String) : Section()

@Serializable
@SerialName("subject")
data class SubjectSection(val id: Int) : Section()

fun main() {
  val jsonString = """
    {"errcode":0,"msg":"ok","data":[{"type":"shop","name":"hello"},{"type":"subject","id":1}]}
  """.trimIndent()
  val result = Json.decodeFromString<ApiResponse<List<Section>>>(jsonString)

  println(Json.encodeToString(result))






}