package algorithms.leetcode.trafficlight

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.actor
import java.util.concurrent.ThreadLocalRandom
import kotlin.time.ExperimentalTime
import kotlin.time.TimeSource

class TrafficLightActor(val scope: CoroutineScope) : SuspendableTrafficLight {

  sealed class Msg
  class CarArrived(val car: Car, val ack: CompletableDeferred<Unit>) : Msg()
  class CarPassed(val car: Car) : Msg()

  @OptIn(ObsoleteCoroutinesApi::class)
  private val actor = scope.actor<Msg> {
    var green = Road.A
    val crossing = hashMapOf<Int, CarArrived>()
    val waiting = hashMapOf<Int, CarArrived>()
    for (msg in channel) {
      when (msg) {
        is CarArrived -> {
          if (msg.car.road == green) {
            msg.ack.complete(Unit)
            crossing[msg.car.id] = msg
          } else {
            waiting[msg.car.id] = msg
          }
        }
        is CarPassed -> {
          crossing.remove(msg.car.id)!!
          if (crossing.isEmpty()) {
            green = !green
            waiting.values.forEach {
              it.ack.complete(Unit)
            }
            crossing.putAll(waiting)
            waiting.clear()
          }
        }
      }
    }
  }

  fun shutdown() {
    actor.close()
  }

  override suspend fun carArrived(car: Car, crossCar: suspend () -> Unit) {
    val ack = CompletableDeferred<Unit>()
    actor.send(CarArrived(car, ack))
    ack.await()
    crossCar()
    actor.send(CarPassed(car))
  }
}

@OptIn(ExperimentalTime::class)
suspend fun main() {
  // Curiously, old coroutine library throws internal error
  // if set to a large count like 5000. Seems fixed in 1.5.1
  val specs: List<Pair<Car, Long>> = testCars(5000)
  val time = TimeSource.Monotonic.markNow()
  coroutineScope {
    val traffic = TrafficLightActor(this)
    coroutineScope {
      specs.forEach {
        val (car, at) = it
        launch {
          val wait = ThreadLocalRandom.current().nextLong(0, 1000)
          delay(wait)
          traffic.carArrived(car) { delay(at) }
          println("$car passed.")
        }
      }
    }
    traffic.shutdown()
    println("took ${time.elapsedNow()}")
  }
}
