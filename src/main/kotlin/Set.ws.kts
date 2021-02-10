data class Carrot(val id: Int, val name: String)

val carrots1: Map<Int, Carrot> = listOf(
  Carrot(1, "1"),
  Carrot(2, "1"),
).associateBy { it.id }

val carrots2: Map<Int, Carrot> = listOf(
  Carrot(3, "1"),
  Carrot(4, "1"),
).associateBy { it.id }

carrots1 + carrots2
carrots1 - carrots2
carrots1.values intersect carrots2.values











