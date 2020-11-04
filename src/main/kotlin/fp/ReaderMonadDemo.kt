package fp

class ReaderMonadDemo {
}

class Reader<D, out A>(val run: (D) -> A) {

  inline fun <B> map(crossinline fa: (A) -> B): Reader<D, B> = Reader {
      d -> fa(run(d))
  }

  inline fun <B> flatMap(crossinline fa: (A) -> Reader<D, B>): Reader<D, B> = Reader {
      d -> fa(run(d)).run(d)
  }

  companion object Factory {
    fun <D, A> just(a: A): Reader<D, A> = Reader { _ -> a }

    fun <D> ask(): Reader<D, D> = Reader { it }
  }
}

data class GetHeroesContext(
  val view: View,
  val getSuperHeroesUseCase: GetSuperHeroesUseCase,
  val heroesRepository: HeroesRepository,
  val dataSources: List<HeroDataSource>
)

class GetSuperHeroesUseCase {
//  fun getSuperHeroes(): Reader<GetHeroesContext, List<SuperHero>> = Reader.ask<GetHeroesContext>().flatMap {
//    it.heroesRepository.getHeroes()
//  }
}
class HeroesRepository {
  fun getHeroes() = listOf(
    SuperHero("Superman"), SuperHero("Spider-Man")
  )
}
class HeroDataSource
class View

class SuperHero(val name: String)



