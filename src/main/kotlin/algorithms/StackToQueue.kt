package algorithms
import java.util.ArrayDeque

class MyQueue() {
  val input = ArrayDeque<Int>()
  val output = ArrayDeque<Int>()

  /** Initialize your data structure here. */

  /** Push element x to the back of queue. */
  fun push(x: Int) {
    input.push(x)
  }

  /** Removes the element from in front of queue and returns that element. */
  fun pop(): Int {
    if (!output.isEmpty()) {
      return output.pop()
    }
    move()
    return output.pop()
  }

  /** Get the front element. */
  fun peek(): Int {
    move()
    return output.peek()
  }

  /** Returns whether the queue is empty. */
  fun empty(): Boolean {
    return output.isEmpty() && input.isEmpty()
  }

  private fun move() {
    while (!input.isEmpty()) {
      output.push(input.pop())
    }
  }
}

fun main() {

//  ["MyQueue","push","push","peek","push","peek"]
//  [[],[1],[2],[],[3],[]]

  val q = MyQueue()
  q.push(1)
  q.push(2)
  q.peek().also { println(it) }
  q.push(3)
  q.peek().also { println(it) }

}