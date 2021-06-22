package ktorm

import ktorm.User.primaryKey
import org.ktorm.database.Database
import org.ktorm.dsl.Query
import org.ktorm.dsl.forEach
import org.ktorm.dsl.from
import org.ktorm.dsl.select
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.support.postgresql.PostgreSqlDialect
import org.ktorm.support.postgresql.textArray


val pg = Database.connect(
  url = "jdbc:postgresql://localhost:49156/postgres",
  user = "postgres",
  password = "123456",
  dialect = PostgreSqlDialect()
)

object PgUser : Table<Nothing>("users") {
  val id = int("id")
  val friends = textArray("friends")
}


fun main() {
  val q: Query = pg.from(PgUser).select(PgUser.id, PgUser.friends)
  q.forEach {
    println("${it[PgUser.id]}, ${it[PgUser.friends]}")
  }

}