package com.adventofcode.util

import kotlin.math.acos
import kotlin.math.sqrt

class Vector(val size: Int, init: (Int) -> Double) {
    companion object {
        fun of(vararg arr: Number): Vector {
            return Vector(arr.size) { i -> arr[i].toDouble() }
        }
    }

    val arr = Array<Double>(size, init)

    override fun toString(): String {
        val sb = StringBuilder("Vec[ ${this[0]} ")
        for (i in 1..<this.size) {
            sb.append(", ${this[i]} ")
        }
        sb.append("]")
        return sb.toString()
    }

    operator fun get(i: Int): Double {
        return arr[i]
    }

    operator fun set(i: Int, v: Double) {
        arr[i] = v
    }

    operator fun plus(v: Vector): Vector {
        return Vector(this.size) { i -> this[i] + v[i] }
    }

    operator fun minus(v: Vector): Vector {
        return Vector(this.size) { i -> this[i] - v[i] }
    }

    operator fun times(v: Vector): Double {
        return this.dotProduct(v)
    }

    operator fun times(m: Matrix): Vector {
        if (this.size != m.rows) {
            throw IllegalArgumentException("Mismatched dimensions")
        }

        return Vector(m.cols) { c -> 
            (0..<this.size).map { k -> this[k] * m[k, c] }.sum()
        }
    }

    operator fun plus(x: Number): Vector {
        return Vector(this.size) { i -> this[i] + x.toDouble() }
    }

    operator fun minus(x: Number): Vector {
        return Vector(this.size) { i -> this[i] - x.toDouble() }
    }

    operator fun times(x: Number): Vector {
        return Vector(this.size) { i -> this[i] * x.toDouble() }
    }

    operator fun div(x: Number): Vector {
        return Vector(this.size) { i -> this[i] / x.toDouble() }
    }

    operator fun rem(x: Number): Vector {
        return Vector(this.size) { i -> this[i] % x.toDouble() }
    }

    fun dotProduct(v: Vector): Double {
        return this.innerProduct(v)
    }

    fun innerProduct(v: Vector): Double {
        if (this.size != v.size) {
            throw IllegalArgumentException("Vectors must be the same size")
        }
        
        return (0..<this.size).map { i -> this[i] * v[i] }.sum()
    }

    fun crossProduct(v: Vector): Vector {
        if (this.size != 3 || v.size != 3) {
            throw IllegalArgumentException("Vectors must have 3 elements each")
        }
        
        return Vector.of(
            this[1] * v[2] - this[2] * v[1],
            this[2] * v[0] - this[0] * v[2],
            this[0] * v[1] - this[1] * v[0]
        )
    }

    fun magnitude(): Double {
        return sqrt((0..<this.size).map { i -> this[i] * this[i] }.sum())
    }

    fun angleBetween(v: Vector): Double {
        return acos(this.dotProduct(v) / (this.magnitude() * v.magnitude()))
    }

    fun normalize(): Vector {
        return this / this.magnitude()
    }

    inline fun toRow(): Matrix {
        return Matrix.ofRows(this)
    }

    inline fun toCol(): Matrix {
        return Matrix.ofCols(this)
    }
}