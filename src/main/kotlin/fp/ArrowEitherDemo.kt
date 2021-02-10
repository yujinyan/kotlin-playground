package fp

import arrow.core.Either
import arrow.core.computations.either
import arrow.core.flatMap
import arrow.core.left
import arrow.core.right
import fp.ArrowEitherDemo.uploadImage
import fp.ArrowEitherDemo.uploadVideo
import kotlin.random.Random

object ArrowEitherDemo {
  fun uploadImage(): Either<NetworkUnstable, String> =
    if (Random.nextBoolean()) NetworkUnstable.left() else "xxx".right()

  fun uploadVideo(): Either<NetworkUnstable, String> =
    if (Random.nextBoolean()) NetworkUnstable.left() else "xxx".right()
}

suspend fun main() {
  val result = either<NetworkUnstable, List<String>> {
    listOf(uploadImage().bind(), uploadVideo().bind())
  }
  println(result)
}
