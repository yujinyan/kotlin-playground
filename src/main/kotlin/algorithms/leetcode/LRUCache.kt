package algorithms.leetcode

class LRUCache(val capacity: Int) {
  private val map = mutableMapOf<Int, Node>()
  private val head = Node(null, null, 0, 0)
  private val tail = Node(null, null, 0, 0)

  init {
    head.next = tail
    tail.prev = head
  }

  fun get(key: Int): Int {
    val node = map[key] ?: return -1
    remove(node)
    insertToHead(node)
    return node.value
  }

  fun put(key: Int, value: Int) {
    if (key in map) {
      val node = map[key]!!
      node.value = value
      remove(node)
      node.insertAfter(head)
      return
    }
    val node = Node(null, null, key, value)
    map[key] = node
    node.insertAfter(head)
    if (map.size > capacity) {
      map.remove(removeTail().key)
    }
  }

  private fun removeTail(): Node {
    val removing = tail.prev!!
    remove(removing)
    return removing
  }

  private fun remove(node: Node) {
    node.prev?.next = node.next
    node.next?.prev = node.prev
  }

  // node -> this
  private fun Node.insertAfter(node: Node) {
    val last = node.next
    node.next = this
    this.prev = node
    this.next = last
    last?.prev = this
  }


  private fun insertToHead(node: Node) {
    val replacing = head.next!!
    replacing.prev = node
    node.prev = head
    node.next = replacing
    head.next = node
  }

  private class Node(
    var prev: Node?,
    var next: Node?,
    val key: Int,
    var value: Int
  ) {
    override fun toString() = key.toString()
  }
}

fun main() {

  with(LRUCache(2)) {
    put(2, 1)
    put(1, 1)
    put(2, 3)
    put(4, 1)
    get(1).also { println(it) } // -1
    get(2).also { println(it) }
  }
//
//  with(LRUCache(2)) {
//    put(1, 1)
//    put(2, 2)
//    get(1)
//    put(3, 3)
//    get(2).also { println(it) }
//    put(4, 4)
//    get(1)
//    get(3)
//    get(4)
//  }

}