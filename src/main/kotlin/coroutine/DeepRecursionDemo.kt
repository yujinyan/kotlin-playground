package coroutine

class Tree(val left: Tree?, val right: Tree?)

@ExperimentalStdlibApi
val depth = DeepRecursiveFunction<Tree?, Int> { tree: Tree? ->
  if (tree == null) 0 else maxOf(
    callRecursive(tree.left),
    callRecursive(tree.right),
  ) + 1
}

@ExperimentalStdlibApi
fun main() {
  val treeGenerator = generateSequence(Tree(null, null)) { prev -> Tree(prev, null) }
  val deepTree = treeGenerator.take(100_000).last()
  println(depth(deepTree))
}