@file:Suppress("MemberVisibilityCanBePrivate")

import DataType.*


//interface DataEntry<T> {
//  val type: DataType
//  val value: T
//}
//
//val d1 = object : DataEntry<Int> {
//  override val type: DataType = UV
//  override val value: Int = 10
//}

enum class DataType {
  UV, PV, Rate
}

sealed class DataEntry<T>(val type: DataType, open val value: T)

class UV(override val value: Int) : DataEntry<Int>(UV, value)
class RetentionRate(override val value: Double) : DataEntry<Double>(Rate, value)

val dataList: List<DataEntry<*>> = listOf(UV(100), RetentionRate(100.0))

fun main() {
  dataList.forEach {
    println(it.type.name)
    println(it.value)
  }
}


