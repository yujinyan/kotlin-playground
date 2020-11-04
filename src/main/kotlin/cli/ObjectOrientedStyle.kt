package cli

import kotlinx.cli.*
import kotlinx.cli.default

abstract class CliCommand : HasParser, HasAfterJobHook {
  override val parser = ArgParser("")

  abstract fun run()

  private val jobs = mutableListOf<() -> Unit>()

  fun main(args: Array<String>) {
    parser.parse(args)
    run()
    jobs.forEach { it() }
  }

  override fun addPostRunJob(job: () -> Unit) {
    jobs.add(job)
  }
}

interface ShouldSendFile : HasOutput, HasAfterJobHook {
  fun outputOption(): SingleOption<String, DefaultRequiredType.Default> {
    val shouldSend by parser.option(ArgType.Boolean, "send").default(false)
    val output = parser.option(ArgType.String).default("output.csv")
    addPostRunJob {
      if (shouldSend) {
        println("sending file to ${output.value}")
      }
    }
    return output
  }
}


interface HasParser {
  val parser: ArgParser
}


interface HasOutput : HasParser {
  val output: String
}

interface HasAfterJobHook {
  fun addPostRunJob(job: () -> Unit)
}

class MyJob : CliCommand(), ShouldSendFile {
  override val output by outputOption()

  override fun run() {
    println("writing to file $output")
  }
}

fun main(args: Array<String>) = MyJob().main(args)
