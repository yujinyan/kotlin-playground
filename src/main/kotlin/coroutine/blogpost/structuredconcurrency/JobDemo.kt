package coroutine.blogpost.structuredconcurrency

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.litote.kmongo.index

@InternalCoroutinesApi
suspend fun main() {
  val job = GlobalScope.launch {
    launch {
      launch { }
      launch { }
    }
    launch {
      launch { }
      launch { }
    }
  }.apply { join() }

  printJobTree(job)
}

fun printJobTree(job: Job, builder: StringBuilder = StringBuilder()) {
  println(builder.toString() + job)
  job.children.forEach {
    printJobTree(it, builder.append("  "))
  }
}

