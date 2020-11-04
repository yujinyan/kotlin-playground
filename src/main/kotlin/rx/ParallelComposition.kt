package rx

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers

fun main() {
  Observable.range(0, 10)
    .flatMap {
      Observable.just(it)
        .subscribeOn(Schedulers.io())
        .map {
          println(Thread.currentThread().name)
          Thread.sleep(500)
          it * it
        }
    }
    .map {
      println(Thread.currentThread())
      it * it
    }
    .blockingSubscribe {
      println(it)
    }
}
