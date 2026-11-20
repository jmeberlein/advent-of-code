package com.adventofcode.util

abstract class Matrix(open override val rows: Int, open override val cols: Int = rows) : Array2D<Number> {
    abstract override operator fun get(r: Int, c: Int): Number
    abstract override operator fun set(r: Int, c: Int, v: Number)

    override fun toString(): String {
        val sb = StringBuilder()
        for (r in 0..<rows) {
            sb.append("[ ${this[r, 0]} ")
            for (c in 1..<cols) sb.append(", ${this[r, c]} ")
            sb.append("]")
            if (r != rows - 1) sb.append("\n")
        }
        return sb.toString()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Matrix || rows != other.rows || cols != other.cols) return false
        return (0..<rows).all { r -> (0..<cols).all { c -> this[r, c].toDouble() == other[r, c].toDouble() } }
    }

    override fun hashCode(): Int {
        var result = rows * 31 + cols
        for (r in 0..<rows) for (c in 0..<cols) result = 31 * result + this[r, c].toDouble().hashCode()
        return result
    }

    abstract override fun transpose(): Matrix
    abstract fun toIntMatrix(): IntMatrix
    abstract fun toDoubleMatrix(): DoubleMatrix
    abstract fun toLongMatrix(): LongMatrix
}
