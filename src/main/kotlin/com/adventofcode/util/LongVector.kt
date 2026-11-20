package com.adventofcode.util

import kotlin.math.abs
import kotlin.math.acos
import kotlin.math.pow

class LongVector(override val size: Int, init: (Int) -> Long) : Vector(size) {
    companion object {
        val LEFT = LongVector.of(0L, -1L)
        val RIGHT = LongVector.of(0L, 1L)
        val UP = LongVector.of(-1L, 0L)
        val DOWN = LongVector.of(1L, 0L)

        fun of(vararg arr: Number): LongVector {
            return LongVector(arr.size) { i -> arr[i].toLong() }
        }
    }

    val arr = LongArray(size, init)

    override operator fun get(index: Int): Long = arr[index]

    override operator fun set(index: Int, value: Number) {
        arr[index] = value.toLong()
    }

    override fun toIntVector(): IntVector = IntVector(size) { i -> this[i].toInt() }
    override fun toDoubleVector(): DoubleVector = DoubleVector(size) { i -> this[i].toDouble() }
    override fun toLongVector(): LongVector = LongVector(size) { i -> this[i] }

    // Addition
    operator fun plus(v: IntVector): LongVector = LongVector(size) { i -> this[i] + v[i] }
    operator fun plus(v: DoubleVector): DoubleVector = DoubleVector(size) { i -> this[i] + v[i] }
    operator fun plus(v: LongVector): LongVector = LongVector(size) { i -> this[i] + v[i] }
    operator fun plus(v: Int): LongVector = LongVector(size) { i -> this[i] + v }
    operator fun plus(v: Double): DoubleVector = DoubleVector(size) { i -> this[i] + v }
    operator fun plus(v: Long): LongVector = LongVector(size) { i -> this[i] + v }

    // Subtraction
    operator fun minus(v: IntVector): LongVector = LongVector(size) { i -> this[i] - v[i] }
    operator fun minus(v: DoubleVector): DoubleVector = DoubleVector(size) { i -> this[i] - v[i] }
    operator fun minus(v: LongVector): LongVector = LongVector(size) { i -> this[i] - v[i] }
    operator fun minus(v: Int): LongVector = LongVector(size) { i -> this[i] - v }
    operator fun minus(v: Double): DoubleVector = DoubleVector(size) { i -> this[i] - v }
    operator fun minus(v: Long): LongVector = LongVector(size) { i -> this[i] - v }

    // Multiplication (Dot Product & Scalar)
    operator fun times(v: IntVector): Long = this.innerProduct(v)
    operator fun times(v: DoubleVector): Double = this.innerProduct(v)
    operator fun times(v: LongVector): Long = this.innerProduct(v)
    operator fun times(v: Int): LongVector = LongVector(size) { i -> this[i] * v }
    operator fun times(v: Double): DoubleVector = DoubleVector(size) { i -> this[i] * v }
    operator fun times(v: Long): LongVector = LongVector(size) { i -> this[i] * v }

    // Division
    operator fun div(v: Int): LongVector = LongVector(size) { i -> this[i] / v }
    operator fun div(v: Double): DoubleVector = DoubleVector(size) { i -> this[i] / v }
    operator fun div(v: Long): LongVector = LongVector(size) { i -> this[i] / v }

    // Remainder
    operator fun rem(v: Int): LongVector = LongVector(size) { i -> this[i] % v }
    operator fun rem(v: Double): DoubleVector = DoubleVector(size) { i -> this[i] % v }
    operator fun rem(v: Long): LongVector = LongVector(size) { i -> this[i] % v }

    // Inner (Dot) Product
    fun innerProduct(v: IntVector): Long {
        require(this.size == v.size) { "Vectors must be the same size" }
        return (0..<this.size).sumOf { i -> this[i] * v[i] }
    }

    fun innerProduct(v: DoubleVector): Double {
        require(this.size == v.size) { "Vectors must be the same size" }
        return (0..<this.size).sumOf { i -> this[i] * v[i] }
    }

    fun innerProduct(v: LongVector): Long {
        require(this.size == v.size) { "Vectors must be the same size" }
        return (0..<this.size).sumOf { i -> this[i] * v[i] }
    }

    // Vector-Matrix (row vector × matrix)
    operator fun times(m: IntMatrix): LongVector {
        require(size == m.rows) { "Mismatched dimensions" }
        return LongVector(m.cols) { j -> (0..<m.rows).sumOf { i -> this[i] * m[i, j] } }
    }
    operator fun times(m: DoubleMatrix): DoubleVector {
        require(size == m.rows) { "Mismatched dimensions" }
        return DoubleVector(m.cols) { j -> (0..<m.rows).sumOf { i -> this[i] * m[i, j] } }
    }
    operator fun times(m: LongMatrix): LongVector {
        require(size == m.rows) { "Mismatched dimensions" }
        return LongVector(m.cols) { j -> (0..<m.rows).sumOf { i -> this[i] * m[i, j] } }
    }

    // Cross Product (3D only)
    fun crossProduct(v: IntVector): LongVector {
        require(this.size == 3 && v.size == 3) { "Vectors must have 3 elements each" }
        return LongVector.of(
            this[1] * v[2] - this[2] * v[1],
            this[2] * v[0] - this[0] * v[2],
            this[0] * v[1] - this[1] * v[0]
        )
    }

    fun crossProduct(v: DoubleVector): DoubleVector {
        require(this.size == 3 && v.size == 3) { "Vectors must have 3 elements each" }
        return DoubleVector.of(
            this[1] * v[2] - this[2] * v[1],
            this[2] * v[0] - this[0] * v[2],
            this[0] * v[1] - this[1] * v[0]
        )
    }

    fun crossProduct(v: LongVector): LongVector {
        require(this.size == 3 && v.size == 3) { "Vectors must have 3 elements each" }
        return LongVector.of(
            this[1] * v[2] - this[2] * v[1],
            this[2] * v[0] - this[0] * v[2],
            this[0] * v[1] - this[1] * v[0]
        )
    }
}
