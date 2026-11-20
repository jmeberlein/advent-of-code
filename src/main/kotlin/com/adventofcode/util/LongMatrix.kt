package com.adventofcode.util

class LongMatrix(override val rows: Int, override val cols: Int = rows, init: (Int, Int) -> Long) : Matrix(rows, cols) {
    companion object {
        fun identity(n: Int) = LongMatrix(n) { r, c -> if (r == c) 1L else 0L }
        fun scalar(n: Int, value: Long) = LongMatrix(n) { r, c -> if (r == c) value else 0L }
        fun diagonal(vararg diag: Long) = LongMatrix(diag.size) { r, c -> if (r == c) diag[r] else 0L }
        fun ofRows(vararg rows: LongVector) = LongMatrix(rows.size, rows[0].size) { r, c -> rows[r][c] }
        fun ofCols(vararg cols: LongVector) = LongMatrix(cols[0].size, cols.size) { r, c -> cols[c][r] }
    }

    val grid = Array(rows) { r -> LongArray(cols) { c -> init(r, c) } }

    override operator fun get(r: Int, c: Int): Long = grid[r][c]
    override operator fun set(r: Int, c: Int, v: Number) { grid[r][c] = v.toLong() }

    override fun transpose(): LongMatrix = LongMatrix(cols, rows) { r, c -> this[c, r] }
    override fun toIntMatrix(): IntMatrix = IntMatrix(rows, cols) { r, c -> this[r, c].toInt() }
    override fun toDoubleMatrix(): DoubleMatrix = DoubleMatrix(rows, cols) { r, c -> this[r, c].toDouble() }
    override fun toLongMatrix(): LongMatrix = LongMatrix(rows, cols) { r, c -> this[r, c] }

    // Matrix-Matrix
    operator fun plus(m: IntMatrix): LongMatrix = LongMatrix(rows, cols) { r, c -> this[r, c] + m[r, c] }
    operator fun plus(m: DoubleMatrix): DoubleMatrix = DoubleMatrix(rows, cols) { r, c -> this[r, c] + m[r, c] }
    operator fun plus(m: LongMatrix): LongMatrix = LongMatrix(rows, cols) { r, c -> this[r, c] + m[r, c] }
    operator fun minus(m: IntMatrix): LongMatrix = LongMatrix(rows, cols) { r, c -> this[r, c] - m[r, c] }
    operator fun minus(m: DoubleMatrix): DoubleMatrix = DoubleMatrix(rows, cols) { r, c -> this[r, c] - m[r, c] }
    operator fun minus(m: LongMatrix): LongMatrix = LongMatrix(rows, cols) { r, c -> this[r, c] - m[r, c] }

    operator fun times(m: IntMatrix): LongMatrix {
        require(cols == m.rows) { "Mismatched dimensions" }
        return LongMatrix(rows, m.cols) { r, c -> (0..<cols).sumOf { k -> this[r, k] * m[k, c] } }
    }
    operator fun times(m: DoubleMatrix): DoubleMatrix {
        require(cols == m.rows) { "Mismatched dimensions" }
        return DoubleMatrix(rows, m.cols) { r, c -> (0..<cols).sumOf { k -> this[r, k] * m[k, c] } }
    }
    operator fun times(m: LongMatrix): LongMatrix {
        require(cols == m.rows) { "Mismatched dimensions" }
        return LongMatrix(rows, m.cols) { r, c -> (0..<cols).sumOf { k -> this[r, k] * m[k, c] } }
    }

    // Matrix-Vector
    operator fun times(v: IntVector): LongVector {
        require(cols == v.size) { "Mismatched dimensions" }
        return LongVector(rows) { r -> (0..<cols).sumOf { k -> this[r, k] * v[k] } }
    }
    operator fun times(v: DoubleVector): DoubleVector {
        require(cols == v.size) { "Mismatched dimensions" }
        return DoubleVector(rows) { r -> (0..<cols).sumOf { k -> this[r, k] * v[k] } }
    }
    operator fun times(v: LongVector): LongVector {
        require(cols == v.size) { "Mismatched dimensions" }
        return LongVector(rows) { r -> (0..<cols).sumOf { k -> this[r, k] * v[k] } }
    }

    // Scalar
    operator fun plus(x: Int): LongMatrix = LongMatrix(rows, cols) { r, c -> this[r, c] + x }
    operator fun plus(x: Double): DoubleMatrix = DoubleMatrix(rows, cols) { r, c -> this[r, c] + x }
    operator fun plus(x: Long): LongMatrix = LongMatrix(rows, cols) { r, c -> this[r, c] + x }
    operator fun minus(x: Int): LongMatrix = LongMatrix(rows, cols) { r, c -> this[r, c] - x }
    operator fun minus(x: Double): DoubleMatrix = DoubleMatrix(rows, cols) { r, c -> this[r, c] - x }
    operator fun minus(x: Long): LongMatrix = LongMatrix(rows, cols) { r, c -> this[r, c] - x }
    operator fun times(x: Int): LongMatrix = LongMatrix(rows, cols) { r, c -> this[r, c] * x }
    operator fun times(x: Double): DoubleMatrix = DoubleMatrix(rows, cols) { r, c -> this[r, c] * x }
    operator fun times(x: Long): LongMatrix = LongMatrix(rows, cols) { r, c -> this[r, c] * x }
    operator fun div(x: Int): LongMatrix = LongMatrix(rows, cols) { r, c -> this[r, c] / x }
    operator fun div(x: Double): DoubleMatrix = DoubleMatrix(rows, cols) { r, c -> this[r, c] / x }
    operator fun div(x: Long): LongMatrix = LongMatrix(rows, cols) { r, c -> this[r, c] / x }
    operator fun rem(x: Int): LongMatrix = LongMatrix(rows, cols) { r, c -> this[r, c] % x }
    operator fun rem(x: Double): DoubleMatrix = DoubleMatrix(rows, cols) { r, c -> this[r, c] % x }
    operator fun rem(x: Long): LongMatrix = LongMatrix(rows, cols) { r, c -> this[r, c] % x }

    fun row(r: Int): LongVector = LongVector(cols) { c -> this[r, c] }
    fun col(c: Int): LongVector = LongVector(rows) { r -> this[r, c] }
}
