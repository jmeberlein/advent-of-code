package com.adventofcode.util

import kotlin.math.acos
import kotlin.math.sqrt
import kotlin.math.pow
import kotlin.math.abs
import kotlin.math.abs

class IntVector(val size: Int, init: (Int) -> Int) {
    companion object {
        val LEFT = IntVector.of(0, -1)
        val RIGHT = IntVector.of(0, 1)
        val UP = IntVector.of(-1, 0)
        val DOWN = IntVector.of(1, 0)

        fun of(vararg arr: Int): IntVector {
            return IntVector(arr.size) { i -> arr[i].toInt() }
        }
    }

    internal val arr = IntArray(size, init)

    override fun toString(): String {
        val sb = StringBuilder("Vec[ ${this[0]} ")
        for (i in 1..<this.size) {
            sb.append(", ${this[i]} ")
        }
        sb.append("]")
        return sb.toString()
    }

    operator fun get(i: Int): Int {
        return arr[i]
    }

    operator fun set(i: Int, v: Int) {
        arr[i] = v
    }

    operator fun plus(v: IntVector): IntVector {
        return IntVector(this.size) { i -> this[i] + v[i] }
    }

    operator fun minus(v: IntVector): IntVector {
        return IntVector(this.size) { i -> this[i] - v[i] }
    }

    operator fun times(v: IntVector): Int {
        return this.dotProduct(v)
    }

    operator fun plus(x: Int): IntVector {
        return IntVector(this.size) { i -> this[i] + x }
    }

    operator fun minus(x: Int): IntVector {
        return IntVector(this.size) { i -> this[i] - x }
    }

    operator fun times(x: Int): IntVector {
        return IntVector(this.size) { i -> this[i] * x }
    }

    operator fun div(x: Int): IntVector {
        return IntVector(this.size) { i -> this[i] / x }
    }

    operator fun rem(x: Int): IntVector {
        return IntVector(this.size) { i -> this[i] % x }
    }

    override fun equals(other: Any?): Boolean {
        when (other) {
            is IntVector -> return (0..<size).all { i -> this[i] == other[i] }
            else -> return false
        }
    }

    override fun hashCode(): Int {
        return arr.contentHashCode()
    }

    fun dotProduct(v: IntVector): Int {
        if (this.size != v.size) {
            throw IllegalArgumentException("IntVectors must be the same size")
        }
        
        return (0..<this.size).map { i -> this[i] * v[i] }.sum()
    }

    fun crossProduct(v: IntVector): IntVector {
        if (this.size != 3 || v.size != 3) {
            throw IllegalArgumentException("IntVectors must have 3 elements each")
        }
        
        return IntVector.of(
            this[1] * v[2] - this[2] * v[1],
            this[2] * v[0] - this[0] * v[2],
            this[0] * v[1] - this[1] * v[0]
        )
    }
}