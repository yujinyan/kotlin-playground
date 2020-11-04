package rx

import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.internal.schedulers.ExecutorScheduler
import io.reactivex.rxjava3.internal.schedulers.NewThreadScheduler
import io.reactivex.rxjava3.internal.schedulers.RxThreadFactory
import io.reactivex.rxjava3.schedulers.Schedulers

fun main() {

  val a: Disposable = Observable.just("hello world")
    .map { // 0
      println("[${Thread.currentThread().name}/map0]:$it")
      it.toUpperCase()
    }
    .subscribeOn(NewThreadScheduler(RxThreadFactory("demo")))
    .map { // 1
      println("[${Thread.currentThread().name}/map1]:$it")
      it.toUpperCase()
    }
    .observeOn(Schedulers.computation())
    .map { // 2
      println("[${Thread.currentThread().name}/map2]:$it")
      it.toUpperCase()
    }
    .observeOn(Schedulers.io())
    .map { // 3
      println("[${Thread.currentThread().name}/map3]:$it")
      it.toUpperCase()
    }
    .subscribe {
      println("[${Thread.currentThread().name}/subscribe]: $it")
    }

  while (true) {

  }
}

