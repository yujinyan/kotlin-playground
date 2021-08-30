package algorithms.leetcode.trafficlight

import java.util.concurrent.CountDownLatch
import java.util.concurrent.Semaphore
import java.util.concurrent.ThreadLocalRandom
import java.util.concurrent.atomic.AtomicInteger
import kotlin.concurrent.thread
import kotlin.time.ExperimentalTime
import kotlin.time.TimeSource

class TrafficLightThread : TrafficLight {
  private val nextPass = Semaphore(0)
  private val passing = mutableListOf<Car>()

  @Volatile
  private var green = Road.A
  private val waiting = AtomicInteger(0)

  override fun carArrived(car: Car, crossCar: Runnable) {
    if (car.road == green) {
      var canPass: Boolean = false
      synchronized(this) {
        if (car.road == green) {
          passing += car
          canPass = true
        }
      }
      if (canPass) {
        crossCar.run()
        carPassedAndCheckLight(car)
        return
      }
    }
    waiting.incrementAndGet()
    nextPass.acquire()
    synchronized(this) {
      passing += car
    }
    crossCar.run()
    carPassedAndCheckLight(car)
  }

  private fun carPassedAndCheckLight(car: Car) {
    synchronized(this) {
      passing -= car
      if (passing.isEmpty()) {
        val count = waiting.getAndUpdate { 0 }
        nextPass.release(count)
      }
    }
  }
}

@OptIn(ExperimentalTime::class)
fun main() {
  val specs: List<Pair<Car, Long>> = testCars(5000)
  val traffic = TrafficLightThread()
  val time = TimeSource.Monotonic.markNow()
  val latch = CountDownLatch(specs.size)
  specs.forEach {
    val (car, at) = it
    thread {
      val wait = ThreadLocalRandom.current().nextLong(0, 1000)
      Thread.sleep(wait)
      traffic.carArrived(car) { Thread.sleep(at) }
      println("$car passed.")
      latch.countDown()
    }
  }
  latch.await()
  println("took ${time.elapsedNow()}")
}