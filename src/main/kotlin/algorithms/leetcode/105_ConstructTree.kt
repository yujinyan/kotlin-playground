package algorithms.leetcode

class TreeNode(
  val `val`: Int,
  var left: TreeNode? = null,
  var right: TreeNode? = null,
)

fun buildTree(preorder: IntArray, inorder: IntArray): TreeNode? {
  fun buildTree(lo: Int, hi: Int): TreeNode? {
    if (hi < lo) return null
    val rootValue = preorder[lo]
    val node = TreeNode(rootValue)
    val iRootIndex = inorder.indexOf(rootValue)
    val leftSize = iRootIndex
    val rightSize = hi - iRootIndex
    node.left = buildTree(lo + 1, lo + leftSize)
    node.right = buildTree(lo + leftSize + 1, lo + leftSize + rightSize)
    return node
  }

  return buildTree(0, preorder.lastIndex)
}

fun main() {
  buildTree(intArrayOf(3, 9, 20, 15, 7), intArrayOf(9, 3, 15, 20, 7))
}