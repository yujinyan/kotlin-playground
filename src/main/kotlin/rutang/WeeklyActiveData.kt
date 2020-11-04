package rutang

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Period
import java.time.temporal.TemporalAdjuster
import java.time.temporal.TemporalAdjusters
import kotlin.time.days

val mondays = sequence<LocalDate> {
  var next = LocalDate.of(2020, 1, 1)
  val end = LocalDate.of(2020, 9, 1)
  while (true) {
    next = next.with(TemporalAdjusters.next(DayOfWeek.MONDAY))
    if (next > end) break
    yield(next)
  }
}

fun main() {
  val sqlStatements = mutableListOf<String>()
  for (monday in mondays) {
    val next = monday + Period.ofDays(7)
    val sql = """
select min(d), sum(r[1]), sum(r[2])
from (
      select min(date) d, retention(toStartOfWeek(date, 1) = '$monday', toStartOfWeek(date, 1) = '$next') r
      from user_daily_api_records
      where toStartOfWeek(date, 1)>= '$monday' and toStartOfWeek(date , 1)<= '$next'
      and city_id = 107
      group by user_id )
    """.trimIndent()
    sqlStatements.add(sql)
  }

  println(sqlStatements.joinToString(" union all "))
}

