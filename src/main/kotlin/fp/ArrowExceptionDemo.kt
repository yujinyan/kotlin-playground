package fp

import arrow.core.Either
import arrow.core.Right
import kotlinx.coroutines.runBlocking
import arrow.typeclasses.*
import arrow.core.extensions.*
import arrow.*
import arrow.core.Left
import arrow.core.extensions.either.monad.flatMap
//import arrow.core.extensions.option.monad.binding
//import arrow.core.extensions.either.monad.binding
import arrow.core.extensions.list.monad.List.monad
import arrow.core.flatMap


sealed class ServiceException

object NetworkUnstable : ServiceException()

object WrongToken: ServiceException()


fun getToken(): Either<ServiceException, String> = Right("token")

fun uploadFile(token: String): Either<ServiceException, String> = Left(WrongToken)

fun sendMessage(messageId: String): Either<ServiceException, String> = Right("success")

fun main() {
  val result: Either<ServiceException, String> = getToken()
    .flatMap { uploadFile(it) }
    .flatMap { sendMessage(it) }
  println(result)

  val result2 = Either.fx<ServiceException, String> {
    val token = getToken().bind()
    val messageId = uploadFile(token).bind()
    sendMessage(messageId).bind()
  }
  println(result2)
}