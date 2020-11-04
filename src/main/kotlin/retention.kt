import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.streams.toList

fun main() {
  val start = LocalDate.of(2020, 6, 1)
  val end = LocalDate.of(2020, 7, 1)

  val simpleFormat = DateTimeFormatter.ISO_DATE

//  val dates = start.datesUntil(end).toList()
//  val sumsPart = dates.mapIndexed { index, _ -> "sum(r[${index + 1}])" }.joinToString()
//  val retentionPart = dates.joinToString { "toDate(created_at) = '${it.format(simpleFormat)}'" }
//
//
//  """
//    select ($sumsPart)
//from (select user_id,
//             retention($retentionPart) as r
//      from subjects
//      where toDate(created_at) >= '2020-05-01'
//        and city_id = 97
//      group by user_id
//      order by user_id
//         )
//  """.let { println(it) }
}