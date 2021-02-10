@file:Suppress("HasPlatformType", "unused")

import java.math.BigInteger
import java.util.*

fun initObject() = BigInteger
  .probablePrime(128, Random())

// singleton, eager
val obj1 = initObject()

// singleton, lazy, thread-safe
val obj2 by lazy { initObject() }



