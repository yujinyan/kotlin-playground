@file:Suppress("UNUSED_PARAMETER")

package fp

import arrow.*
import arrow.core.*
import arrow.core.computations.either
import arrow.core.extensions.*
import arrow.typeclasses.*
import kotlin.random.Random


sealed class ServiceException
object NetworkUnstable : ServiceException()
object WrongToken : ServiceException()

fun getToken(): Either<ServiceException, String> =
  if (Random.nextBoolean()) "token".right() else NetworkUnstable.left()

fun uploadFile(token: String): Either<ServiceException, String> =
  if (Random.nextBoolean()) WrongToken.left() else "success".right()

fun sendMessage(messageId: String): Either<ServiceException, String> = "success".right()

suspend fun doWork() = either<ServiceException, String> {
  val token = getToken().bind()
  val messageId = uploadFile(token).bind()
  return@either sendMessage(messageId).bind()
}

suspend fun main() {
  doWork().also {
    println(it)
  }
}


