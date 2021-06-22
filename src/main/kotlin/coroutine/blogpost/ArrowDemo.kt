package coroutine.blogpost

import arrow.core.Either
import arrow.core.computations.either
import arrow.core.flatMap
import arrow.core.left
import arrow.core.right
import coroutine.blogpost.CookingException.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope

sealed class CookingException {
  object LettuceIsRotten : CookingException()
  object KnifeNeedsSharpening : CookingException()
  data class InsufficientAmount(val quantityInGrams: Int) : CookingException()
}

object Lettuce; object Knife; object Salad

fun takeFoodFromRefrigerator(): Either<LettuceIsRotten, Lettuce> = Lettuce.right()
fun getKnife(): Either<KnifeNeedsSharpening, Knife> = Knife.right()
fun lunch(knife: Knife, food: Lettuce): Either<InsufficientAmount, Salad> = InsufficientAmount(5).left()

fun getSalad(): Either<CookingException, Salad> =
  takeFoodFromRefrigerator()
    .flatMap { lettuce ->
      getKnife()
        .flatMap { knife ->
          val salad = lunch(knife, lettuce)
          salad
        }
    }

suspend fun getSalad2() = either<CookingException, Salad> {
  val lettuce = takeFoodFromRefrigerator().bind()
  val knife = getKnife().bind()
  val salad = lunch(knife, lettuce).bind()
  salad
}

suspend fun main() {
//  CoroutineScope()
  val salad = either<CookingException, Salad> {
    val lettuce = takeFoodFromRefrigerator().bind()
    val knife = getKnife().bind()
    val salad = lunch(knife, lettuce).bind()
    salad
  }
  println(salad)
}
