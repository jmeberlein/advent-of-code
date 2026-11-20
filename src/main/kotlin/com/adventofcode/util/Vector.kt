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

class IntVector(override val size: Int, init: (Int) -> Int) : Vector(size) {
    companion object {
        val LEFT = IntVector.of(0, -1)
        val RIGHT = IntVector.of(0, 1)
        val UP = IntVector.of(-1, 0)
        val DOWN = IntVector.of(1, 0)

        fun of(vararg arr: Number): IntVector {
            return IntVector(arr.size) { i -> arr[i].toInt() }
        }
    }

    val arr = IntArray(size, init)

    override operator fun get(index: Int): Int = arr[index]

    override operator fun set(index: Int, value: Number) {
        arr[index] = value.toInt()
    }

    override fun toIntVector(): IntVector = IntVector(size) { i -> this[i] }
    override fun toDoubleVector(): DoubleVector = DoubleVector(size) { i -> this[i].toDouble() }
    override fun toLongVector(): LongVector = LongVector(size) { i -> this[i].toLong() }

    // Addition
    operator fun plus(v: IntVector): IntVector = IntVector(size) { i -> this[i] + v[i] }
    operator fun plus(v: DoubleVector): DoubleVector = DoubleVector(size) { i -> this[i] + v[i] }
    operator fun plus(v: LongVector): LongVector = LongVector(size) { i -> this[i] + v[i] }
    operator fun plus(v: Int): IntVector = IntVector(size) { i -> this[i] + v }
    operator fun plus(v: Double): DoubleVector = DoubleVector(size) { i -> this[i] + v }
    operator fun plus(v: Long): LongVector = LongVector(size) { i -> this[i] + v }

    // Subtraction
    operator fun minus(v: IntVector): IntVector = IntVector(size) { i -> this[i] - v[i] }
    operator fun minus(v: DoubleVector): DoubleVector = DoubleVector(size) { i -> this[i] - v[i] }
    operator fun minus(v: LongVector): LongVector = LongVector(size) { i -> this[i] - v[i] }
    operator fun minus(v: Int): IntVector = IntVector(size) { i -> this[i] - v }
    operator fun minus(v: Double): DoubleVector = DoubleVector(size) { i -> this[i] - v }
    operator fun minus(v: Long): LongVector = LongVector(size) { i -> this[i] - v }

    // Multiplication (Dot Product & Scalar)
    operator fun times(v: IntVector): Int = this.innerProduct(v)
    operator fun times(v: DoubleVector): Double = this.innerProduct(v)
    operator fun times(v: LongVector): Long = this.innerProduct(v)
    operator fun times(v: Int): IntVector = IntVector(size) { i -> this[i] * v }
    operator fun times(v: Double): DoubleVector = DoubleVector(size) { i -> this[i] * v }
    operator fun times(v: Long): LongVector = LongVector(size) { i -> this[i] * v }

    // Division
    operator fun div(v: Int): IntVector = IntVector(size) { i -> this[i] / v }
    operator fun div(v: Double): DoubleVector = DoubleVector(size) { i -> this[i] / v }
    operator fun div(v: Long): LongVector = LongVector(size) { i -> this[i] / v }

    // Remainder
    operator fun rem(v: Int): IntVector = IntVector(size) { i -> this[i] % v }
    operator fun rem(v: Double): DoubleVector = DoubleVector(size) { i -> this[i] % v }
    operator fun rem(v: Long): LongVector = LongVector(size) { i -> this[i] % v }

    // Inner (Dot) Product
    fun innerProduct(v: IntVector): Int {
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

    // Cross Product (3D only)
    fun crossProduct(v: IntVector): IntVector {
        require(this.size == 3 && v.size == 3) { "Vectors must have 3 elements each" }
        return IntVector.of(
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

class DoubleVector(override val size: Int, init: (Int) -> Double) : Vector(size) {
    companion object {
        val LEFT = DoubleVector.of(0.0, -1.0)
        val RIGHT = DoubleVector.of(0.0, 1.0)
        val UP = DoubleVector.of(-1.0, 0.0)
        val DOWN = DoubleVector.of(1.0, 0.0)

        fun of(vararg arr: Number): DoubleVector {
            return DoubleVector(arr.size) { i -> arr[i].toDouble() }
        }
    }

    val arr = DoubleArray(size, init)

    override operator fun get(index: Int): Double = arr[index]

    override operator fun set(index: Int, value: Number) {
        arr[index] = value.toDouble()
    }

    override fun toIntVector(): IntVector = IntVector(size) { i -> this[i].toInt() }
    override fun toDoubleVector(): DoubleVector = DoubleVector(size) { i -> this[i] }
    override fun toLongVector(): LongVector = LongVector(size) { i -> this[i].toLong() }

    // Addition
    operator fun plus(v: IntVector): DoubleVector = DoubleVector(size) { i -> this[i] + v[i] }
    operator fun plus(v: DoubleVector): DoubleVector = DoubleVector(size) { i -> this[i] + v[i] }
    operator fun plus(v: LongVector): DoubleVector = DoubleVector(size) { i -> this[i] + v[i] }
    operator fun plus(v: Int): DoubleVector = DoubleVector(size) { i -> this[i] + v }
    operator fun plus(v: Double): DoubleVector = DoubleVector(size) { i -> this[i] + v }
    operator fun plus(v: Long): DoubleVector = DoubleVector(size) { i -> this[i] + v }

    // Subtraction
    operator fun minus(v: IntVector): DoubleVector = DoubleVector(size) { i -> this[i] - v[i] }
    operator fun minus(v: DoubleVector): DoubleVector = DoubleVector(size) { i -> this[i] - v[i] }
    operator fun minus(v: LongVector): DoubleVector = DoubleVector(size) { i -> this[i] - v[i] }
    operator fun minus(v: Int): DoubleVector = DoubleVector(size) { i -> this[i] - v }
    operator fun minus(v: Double): DoubleVector = DoubleVector(size) { i -> this[i] - v }
    operator fun minus(v: Long): DoubleVector = DoubleVector(size) { i -> this[i] - v }

    // Multiplication (Dot Product & Scalar)
    operator fun times(v: IntVector): Double = this.innerProduct(v)
    operator fun times(v: DoubleVector): Double = this.innerProduct(v)
    operator fun times(v: LongVector): Double = this.innerProduct(v)
    operator fun times(v: Int): DoubleVector = DoubleVector(size) { i -> this[i] * v }
    operator fun times(v: Double): DoubleVector = DoubleVector(size) { i -> this[i] * v }
    operator fun times(v: Long): DoubleVector = DoubleVector(size) { i -> this[i] * v }

    // Division
    operator fun div(v: Int): DoubleVector = DoubleVector(size) { i -> this[i] / v }
    operator fun div(v: Double): DoubleVector = DoubleVector(size) { i -> this[i] / v }
    operator fun div(v: Long): DoubleVector = DoubleVector(size) { i -> this[i] / v }

    // Remainder
    operator fun rem(v: Int): DoubleVector = DoubleVector(size) { i -> this[i] % v }
    operator fun rem(v: Double): DoubleVector = DoubleVector(size) { i -> this[i] % v }
    operator fun rem(v: Long): DoubleVector = DoubleVector(size) { i -> this[i] % v }

    // Inner (Dot) Product
    fun innerProduct(v: IntVector): Double {
        require(this.size == v.size) { "Vectors must be the same size" }
        return (0..<this.size).sumOf { i -> this[i] * v[i] }
    }

    fun innerProduct(v: DoubleVector): Double {
        require(this.size == v.size) { "Vectors must be the same size" }
        return (0..<this.size).sumOf { i -> this[i] * v[i] }
    }

    fun innerProduct(v: LongVector): Double {
        require(this.size == v.size) { "Vectors must be the same size" }
        return (0..<this.size).sumOf { i -> this[i] * v[i] }
    }

    // Cross Product (3D only)
    fun crossProduct(v: IntVector): DoubleVector {
        require(this.size == 3 && v.size == 3) { "Vectors must have 3 elements each" }
        return DoubleVector.of(
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

    fun crossProduct(v: LongVector): DoubleVector {
        require(this.size == 3 && v.size == 3) { "Vectors must have 3 elements each" }
        return DoubleVector.of(
            this[1] * v[2] - this[2] * v[1],
            this[2] * v[0] - this[0] * v[2],
            this[0] * v[1] - this[1] * v[0]
        )
    }
}