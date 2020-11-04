package ktorm

import io.ktor.util.*
import me.liuwj.ktorm.database.Database
import me.liuwj.ktorm.dsl.*
import me.liuwj.ktorm.schema.Table
import me.liuwj.ktorm.schema.int
import me.liuwj.ktorm.schema.varchar
import kotlin.collections.forEach

val database = Database.connect(
  url = "jdbc:mysql://api.beta.jojotu.cn:3306/jojotoo_test",
  user = "test",
  password = "a0a40zDod4HjM84t"
)

object User : Table<Nothing>("user") {
  val id = int("id").primaryKey()
  val name = varchar("user_nickname")
  val tel = varchar("user_tel_zone")
  val cityId = int("city_id")
}

fun main() {
  val q1 = database.from(User).select(User.id, User.name, User.tel)
    .limit(10, 10)
  val q2 = q1.where {
    User.cityId eq 1
  }
  println(q2.sql)
//    .forEach {
//      println("id: ${it[User.id]}, name: ${it[User.name]}")
//    }
}