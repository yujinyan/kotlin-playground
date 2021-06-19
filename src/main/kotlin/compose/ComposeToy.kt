@file:Suppress("FunctionName")

package compose

/**
 * http://intelligiblebabble.com/compose-from-first-principles/
 */

interface Composer {
  fun emit(node: Node, content: () -> Unit = {})
}

class ComposerImpl(root: Node) : Composer {
  private var current: Node = root

  override fun emit(node: Node, content: () -> Unit) {
    // store current parent to restore later
    val parent = current
    parent.children.add(node)
    current = node
    // with `current` set to `node`, we execute the passed in lambda
    // in the "scope" of it, so that emitted nodes inside of this
    // lambda end up as children to `node`.
    content()
    // restore current
    current = parent
  }
}

class CachingComposerImpl {
  private var cache = mutableListOf<Any?>()
  private var index = 0
  private val inserting get() = index == cache.size
  private fun get(): Any? = cache[index++]
  private fun set(value: Any?) {
    if (inserting) { index++; cache.add(value); }
    else cache[index++] = value
  }

  private fun <T> changed(value: T): Boolean {
    // if we are inserting, we have nothing to compare against,
    // so just store it and return
    return if (inserting) {
      set(value)
      false
    } else {
      // get current item, increment index. always store new
      // value, but return true only if they don't compare
      val index = index++
      val item = cache[index]
      cache[index] = value
      item != value
    }
  }

  private fun <T> cache(update: Boolean, factory: () -> T): T {
    // if we are asked to update the value, or if it is the first time
    // the cache is consulted, we need to execute the factory, and save
    // the result
    return if (inserting || update) factory().also { set(it) }
    // otherwise, just return the value in the cache
    else get() as T
  }

//  override fun <T> Composer.memo(vararg inputs: Any?, factory: () -> T): T {
//    var valid = true
//    // we need to make sure we check every input, every time. no short-circuiting.
//    for (input in inputs) {
//      // it is not valid if any of the inputs have changed from last time
//      valid = !changed(input) && valid
//    }
//    return cache(!valid) { factory() }
//  }
}

abstract class Node {
  val children = mutableListOf<Node>()
  override fun toString(): String {
    return this::class.simpleName!!
  }
}

enum class Orientation { Vertical, Horizontal }

class Stack(var orientation: Orientation) : Node() {
  override fun toString(): String {
    return "${this::class.simpleName}($orientation)"
  }
}

class Text(var text: String) : Node() {
  override fun toString(): String {
    return "Text($text)"
  }
}

fun renderNodeToScreen(node: Node, indent: Int = 0) { /* ... */
  repeat(indent) { print(" ") }
  println(node)
  node.children.forEach {
    renderNodeToScreen(it, indent + 4)
  }
}

class TodoItem(val title: String)

fun TodoApp(items: List<TodoItem>): Node {
  return Stack(Orientation.Vertical).apply {
    for (item in items) {
      children.add(
        Stack(Orientation.Horizontal).apply {
          children.add(Text(item.title))
        }
      )
    }
  }
}

fun Composer.TodoApp(items: List<TodoItem>) {
  emit(Stack(Orientation.Vertical)) {
    for (item in items) {
      emit(Stack(Orientation.Horizontal)) {
        emit(Text(item.title))
      }
    }
  }
}

fun compose(content: Composer.() -> Unit): Node {
  return Stack(Orientation.Vertical).also {
    ComposerImpl(it).content()
  }
}


fun main() {
  val items = listOf(TodoItem("Homework"), TodoItem("Reading"))

  renderNodeToScreen(compose { TodoApp(items) })
}