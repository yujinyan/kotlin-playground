package cli

import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.default

fun useParser(block: ArgParser.() -> Unit) {
  val parser = ArgParser("")
  parser.block()
}


fun main(args: Array<String>) = useParser {
  val output by option(ArgType.String).default("output.csv")
  val shouldSend by option(ArgType.Boolean).default(false)

  parse(args)

  println("writing to file $output")

  if (shouldSend) {
    println("sending file $shouldSend")
  }
}

