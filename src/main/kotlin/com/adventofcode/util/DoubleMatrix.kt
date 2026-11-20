package com.adventofcode.util

class DoubleMatrix(override val rows: Int, override val cols: Int = rows, init: (Int, Int) -> Double) : Matrix(rows, cols) {
    companion object {
        val CCW = ofCols(DoubleVector.of(0.0, 1.0), DoubleVector.of(-1.0, 0.0))
        val CW = ofCols(DoubleVector.of(0.0, -1.0), DoubleVector.of(1.0, 0.0))

        fun identity(n: Int) = DoubleMatrix(n) { r, c -> if (r == c) 1.0 else 0.0 }
        fun scalar(n: Int, value: Double) = DoubleMatrix(n) { r, c -> if (r == c) value else 0.0 }
        fun diagonal(vararg diag: Double) = DoubleMatrix(diag.size) { r, c -> if (r == c) diag[r] else 0.0 }
        fun ofRows(vararg rows: DoubleVector) = DoubleMatrix(rows.size, rows[0].size) { r, c -> rows[r][c] }
        fun ofCols(vararg cols: DoubleVector) = DoubleMatrix(cols[0].size, cols.size) { r, c -> cols[c][r] }
    }

    val grid = Array(rows) { r -> DoubleArray(cols) { c -> init(r, c) } }

    override operator fun get(r: Int, c: Int): Double = grid[r][c]
    override operator fun set(r: Int, c: Int, v: Number) { grid[r][c] = v.toDouble() }

    override fun transpose(): DoubleMatrix = DoubleMatrix(cols, rows) { r, c -> this[c, r] }
    override fun toIntMatrix(): IntMatrix = IntMatrix(rows, cols) { r, c -> this[r, c].toInt() }
    override fun toDoubleMatrix(): DoubleMatrix = DoubleMatrix(rows, cols) { r, c -> this[r, c] }
    override fun toLongMatrix(): LongMatrix = LongMatrix(rows, cols) { r, c -> this[r, c].toLong() }

    // Matrix-Matrix
    operator fun plus(m: IntMatrix): DoubleMatrix = DoubleMatrix(rows, cols) { r, c -> this[r, c] + m[r, c] }
    operator fun plus(m: DoubleMatrix): DoubleMatrix = DoubleMatrix(rows, cols) { r, c -> this[r, c] + m[r, c] }
    operator fun plus(m: LongMatrix): DoubleMatrix = DoubleMatrix(rows, cols) { r, c -> this[r, c] + m[r, c] }
    operator fun minus(m: IntMatrix): DoubleMatrix = DoubleMatrix(rows, cols) { r, c -> this[r, c] - m[r, c] }
    operator fun minus(m: DoubleMatrix): DoubleMatrix = DoubleMatrix(rows, cols) { r, c -> this[r, c] - m[r, c] }
    operator fun minus(m: LongMatrix): DoubleMatrix = DoubleMatrix(rows, cols) { r, c -> this[r, c] - m[r, c] }

    operator fun times(m: IntMatrix): DoubleMatrix {
        require(cols == m.rows) { "Mismatched dimensions" }
        return DoubleMatrix(rows, m.cols) { r, c -> (0..<cols).sumOf { k -> this[r, k] * m[k, c] } }
    }
    operator fun times(m: DoubleMatrix): DoubleMatrix {
        require(cols == m.rows) { "Mismatched dimensions" }
        return DoubleMatrix(rows, m.cols) { r, c -> (0..<cols).sumOf { k -> this[r, k] * m[k, c] } }
    }
    operator fun times(m: LongMatrix): DoubleMatrix {
        require(cols == m.rows) { "Mismatched dimensions" }
        return DoubleMatrix(rows, m.cols) { r, c -> (0..<cols).sumOf { k -> this[r, k] * m[k, c] } }
    }

    // Matrix-Vector
    operator fun times(v: IntVector): DoubleVector {
        require(cols == v.size) { "Mismatched dimensions" }
        return DoubleVector(rows) { r -> (0..<cols).sumOf { k -> this[r, k] * v[k] } }
    }
    operator fun times(v: DoubleVector): DoubleVector {
        require(cols == v.size) { "Mismatched dimensions" }
        return DoubleVector(rows) { r -> (0..<cols).sumOf { k -> this[r, k] * v[k] } }
    }
    operator fun times(v: LongVector): DoubleVector {
        require(cols == v.size) { "Mismatched dimensions" }
        return DoubleVector(rows) { r -> (0..<cols).sumOf { k -> this[r, k] * v[k] } }
    }

    // Scalar
    operator fun plus(x: Int): DoubleMatrix = DoubleMatrix(rows, cols) { r, c -> this[r, c] + x }
    operator fun plus(x: Double): DoubleMatrix = DoubleMatrix(rows, cols) { r, c -> this[r, c] + x }
    operator fun plus(x: Long): DoubleMatrix = DoubleMatrix(rows, cols) { r, c -> this[r, c] + x }
    operator fun minus(x: Int): DoubleMatrix = DoubleMatrix(rows, cols) { r, c -> this[r, c] - x }
    operator fun minus(x: Double): DoubleMatrix = DoubleMatrix(rows, cols) { r, c -> this[r, c] - x }
    operator fun minus(x: Long): DoubleMatrix = DoubleMatrix(rows, cols) { r, c -> this[r, c] - x }
    operator fun times(x: Int): DoubleMatrix = DoubleMatrix(rows, cols) { r, c -> this[r, c] * x }
    operator fun times(x: Double): DoubleMatrix = DoubleMatrix(rows, cols) { r, c -> this[r, c] * x }
    operator fun times(x: Long): DoubleMatrix = DoubleMatrix(rows, cols) { r, c -> this[r, c] * x }
    operator fun div(x: Int): DoubleMatrix = DoubleMatrix(rows, cols) { r, c -> this[r, c] / x }
    operator fun div(x: Double): DoubleMatrix = DoubleMatrix(rows, cols) { r, c -> this[r, c] / x }
    operator fun div(x: Long): DoubleMatrix = DoubleMatrix(rows, cols) { r, c -> this[r, c] / x }
    operator fun rem(x: Int): DoubleMatrix = DoubleMatrix(rows, cols) { r, c -> this[r, c] % x }
    operator fun rem(x: Double): DoubleMatrix = DoubleMatrix(rows, cols) { r, c -> this[r, c] % x }
    operator fun rem(x: Long): DoubleMatrix = DoubleMatrix(rows, cols) { r, c -> this[r, c] % x }

    fun row(r: Int): DoubleVector = DoubleVector(cols) { c -> this[r, c] }
    fun col(c: Int): DoubleVector = DoubleVector(rows) { r -> this[r, c] }
}
