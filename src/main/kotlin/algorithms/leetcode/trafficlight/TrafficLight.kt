package algorithms.leetcode.trafficlight

import java.util.concurrent.ThreadLocalRandom


/**
 * Inspiration:
 * https://leetcode-cn.com/problems/traffic-light-controlled-intersection/
 */

data class Car(val id: Int, val road: Road)

// Either Road A or Road B is green light at the same time
enum class Road {
  A, B;

  operator fun not(): Road = if (this == A) B else A
}

// Impl by threads
interface TrafficLight {
  // Called by concurrent threads.
  fun carArrived(
    car: Car,
    crossCar: Runnable
  )
}

// Impl by coroutines
interface SuspendableTrafficLight {
  // Called by concurrent coroutines.
  suspend fun carArrived(
    car: Car,
    crossCar: suspend () -> Unit
  )
}

fun testCars(count: Int = 500): List<Pair<Car, Long>> = (1..count).map {
  Car(it, if (ThreadLocalRandom.current().nextBoolean()) Road.A else Road.B) to
      1000 + ThreadLocalRandom.current().nextLong(0, 1000)
}




