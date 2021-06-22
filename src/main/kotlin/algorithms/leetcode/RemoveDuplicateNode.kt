package algorithms.leetcode

data class ListNode(
  val value: Int,
  var next: ListNode? = null
) : Iterable<Int> {
  fun toDebugString() = "[${joinToString()}]"

  override fun toString(): String {
    return value.toString()
  }

  override fun iterator(): Iterator<Int> {
    return object : Iterator<Int> {
      var next: ListNode? = this@ListNode
      override fun hasNext(): Boolean {
        return next != null
      }

      override fun next(): Int {
        return next!!.value.also {
          next = next?.next
        }
      }
    }
  }
}

fun listNodesOf(vararg num: Int): ListNode? {
  if (num.isEmpty()) return null
  val head = ListNode(num.first())
  var cur: ListNode? = head
  for (i in 1..num.lastIndex) {
    cur?.next = ListNode(num[i])
    cur = cur?.next
  }
  return head
}

fun removeDuplicateNodes(head: ListNode?): ListNode? {
  val set = hashSetOf<Int>()

  var prev: ListNode? = null
  var cur = head

  while (cur != null) {
    if (cur.value in set) {
      prev?.next = cur.next
    } else {
      set += cur.value
    }
    prev = cur
    cur = cur.next
  }

  return head
}

//  [1, 2, 3, 3, 2, 1]
fun main() {
  val head = listNodesOf(1, 2, 3, 3, 2, 1)
  removeDuplicateNodes(head).also { println(it?.toDebugString()) }

}