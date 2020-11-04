package datav2

sealed class DataEntry<T>(open val value: T)

class UV(value: Int) : DataEntry<Int>(value)
class RetentionRate(value: Double) : DataEntry<Double>(value)

val dataList: List<DataEntry<*>> = listOf(UV(100), RetentionRate(100.0))

