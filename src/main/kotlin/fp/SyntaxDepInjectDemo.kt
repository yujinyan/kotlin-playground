package fp

class DslContext
class HttpClient

class Subject {
  var a: String = ""
    get() = "old"
    private set

  companion object {
    fun batchUpdate(subjects: List<Subject>) {
      subjects.forEach {
        it.a = "new"
      }
    }
  }
}

class Poi

interface FindSubjects {
  val context: DslContext
  fun findByIds(ids: List<Int>): List<Subject> = TODO()
}

interface FindPoi {
  val client: HttpClient
  val context: DslContext
  fun findPoiById(id: String): Poi = TODO()
}

//---------------------- notice interface has multiple-inheritance
interface LoadSubjectWithPoi : FindSubjects, FindPoi {
  fun load(ids: List<Int>) {
    val poi = findPoiById("hi")
    val subjects = findByIds(ids)
    // do stuff with poi and subjects
  }
}

fun main() {
  val loader = object : LoadSubjectWithPoi {
    override val context = DslContext()
    override val client = HttpClient()
  }
  loader.load(listOf(1, 2, 3))
}