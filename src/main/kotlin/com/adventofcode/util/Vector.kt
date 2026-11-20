package com.adventofcode.util

import kotlin.math.abs
import kotlin.math.acos
import kotlin.math.pow

abstract class Vector(open val size: Int) {
    abstract operator fun get(index: Int): Number
    abstract operator fun set(index: Int, value: Number)

    override fun toString(): String {
        if (size == 0) return "Vec[]"
        val sb = StringBuilder("Vec[ ${this[0]} ")
        for (i in 1..<this.size) {
            sb.append(", ${this[i]} ")
        }
        sb.append("]")
        return sb.toString()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Vector || this.size != other.size) return false
        return (0..<size).all { i -> this[i].toDouble() == other[i].toDouble() }
    }

    override fun hashCode(): Int {
        var result = size
        for (i in 0..<size) {
            result = 31 * result + this[i].toDouble().hashCode()
        }
        return result
    }

    fun magnitude(norm: Double = 2.0): Double {
        if (norm <= 0) {
            throw IllegalArgumentException("Norm must be positive")
        } else if (norm == Double.POSITIVE_INFINITY) {
            return (0..<this.size).maxOf { i -> abs(this[i].toDouble()) }
        } else {
            return (0..<this.size).sumOf { i -> abs(this[i].toDouble()).pow(norm) }.pow(1.0 / norm)
        }
    }

    fun angleWith(v: Vector): Double {
        if (this.size != v.size) {
            throw IllegalArgumentException("Vectors must be the same size")
        }
        val dot = (0..<this.size).sumOf { i -> this[i].toDouble() * v[i].toDouble() }
        return acos(dot / (this.magnitude() * v.magnitude()))
    }

    abstract fun toIntVector(): IntVector
    abstract fun toDoubleVector(): DoubleVector
    abstract fun toLongVector(): LongVector
}
