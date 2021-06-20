package ktorm

import io.ktor.util.*
import org.ktorm.database.Database
import org.ktorm.dsl.*
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar
import org.ktorm.support.mysql.insertOrUpdate

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


  val u1 = User.aliased("u1")
  val u2 = User.aliased("u2")
  database.from(u1)

  User.insertOrUpdate {  }
}