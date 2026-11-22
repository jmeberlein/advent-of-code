package com.adventofcode.util

fun Int.floorPowerOfTwo(): Int {
    var x = this
    x = x or (x ushr 1)
    x = x or (x ushr 2)
    x = x or (x ushr 4)
    x = x or (x ushr 8)
    x = x or (x ushr 16)
    return x - (x ushr 1)
}