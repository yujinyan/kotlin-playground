package algorithms.leetcode.backtrack

private fun gen(
  left: Int, right: Int, str: String,
  depth: Int,
  block: (String) -> Unit,
) {
  repeat(depth * 4) {
    print(" ")
  }
  print("($left, $right, \"$str\")")
  if (left == 0 && right == 0) {
    block(str)
    print("  // collect: $str\n")
    return
  }
  print("\n")
  val nextDepth = depth + 1
  if (left > 0) gen(left - 1, right + 1, str + '(', nextDepth, block)
  if (right > 0) gen(left, right - 1, str + ')', nextDepth, block)
}

fun main() {
  val result = mutableListOf<String>()

  gen(3, 0, "", 0) { result += it }
}

