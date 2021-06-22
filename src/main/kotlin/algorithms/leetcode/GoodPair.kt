package algorithms.leetcode

import java.util.HashMap


fun countNicePairs(nums: IntArray): Int {
  // int[] sub = new int[nums.length];
  val map: MutableMap<Int, Int> = HashMap()
  for (i in nums.indices) {
    val result = nums[i] - rev(nums[i])
    val newValue = map.getOrDefault(result, 0) + 1
    map[result] = newValue
  }
  var ret = 0
  for (n in map.values) {
    if (n > 1)
      ret += fact(n) / (fact(n - 2) * 2)
  }
  return ret
  // return fact(n) / fact(n - 1);

  //int[] revs = new int[nums.length];
  //for (int i = 0; i < nums.length; i++) {
  //    revs[i] = rev(nums[i]);
  //}
  //int ret = 0;
  //for (int i = 0; i < nums.length; i++) {
  //    for (int j = i + 1; j < nums.length; j++) {
  //        if (nums[i] + revs[j] == nums[j] + revs[i]) {
  //            ret++;
  //        }
  //    }
  //}
  //return ret;
}

private fun c(n: Int): Int {
//  n *

  TODO()
}

private fun fact(n: Int): Int {
  var ret = 1
  for (i in 1..n) ret *= i
  return ret
}

private fun rev(n: Int): Int {
  var n = n
  var ret = 0
  val t = 10
  while (n != 0) {
    ret = ret * 10 + n % t
    n /= 10
  }
  return ret
}


fun main() {
  countNicePairs(intArrayOf(8047408,
    192867140,
    497837845,
    279787822,
    151999002,
    168514912,
    193424242,
    399636844,
    132424231,
    476736524,
    267958611,
    493350382,
    476382727,
    232939232,
    197000791,
    295291645,
    126313621,
    374645524,
    7956597,
    1376731,
    496463745,
    234481430,
    359130803,
    287625836,
    250572050,
    42311324,
    477434624,
    493231448,
    493231244,
    150494051,
    184645534,
    365252413,
    495764582,
    335976531,
    384564332,
    377151623,
    198736741,
    335161533,
    245552540,
    194897341,
    83911938,
    220562020,
    496645745,
    287151782,
    374635526,
    372483324,
    485101584,
    271797172,
    244949442,
    254333303,
    251635002,
    459181805,
    472392123,
    241350140,
    256121502,
    336895621,
    354635302,
    358909704,
    194525491,
    3606063,
    194150341,
    63477436,
    341936141,
    60299206,
    69811896,
    369928813,
    229926920,
    435310522,
    299542980,
    463777364,
    164534512,
    305885501,
    437181734,
    74288247,
    487281835,
    171161022,
    423966312,
    496989544,
    452633252,
    252433101,
    141565141,
    315895501,
    478897927,
    232532230,
    472451262,
    160504114,
    476666674,
    6179716,
    251483002,
    474777474,
    443532332,
    475808424,
    457514604,
    400936002,
    384878483,
    172616122,
    283292232,
    165645615,
    392000144,
    378636873))
//  countNicePairs(intArrayOf(432835222, 112141211, 5408045, 456281503, 283322436, 414281561, 37773, 286505682))
//  fact(3).also {println(it)}
//  countNicePairs(intArrayOf(42,11,1,97)).also { println(it) }
//  countNicePairs(intArrayOf(13,10,35,24,76)).also{ println(it) }
}