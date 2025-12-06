package com.adventofcode.util

class Matrix(override val rows: Int, override val cols: Int = rows, init: (Int, Int) -> Double): Array2D<Double> {
    companion object {
        fun identity(n: Int): Matrix {
            return Matrix(n) { r, c -> if (r == c) 1.0 else 0.0 }
        }

        fun scalar(n: Int, value: Double): Matrix {
            return Matrix(n) { r, c -> if (r == c) value else 0.0 }
        }

        fun diagonal(vararg diag: Double): Matrix {
            return Matrix(diag.size) { r, c -> if (r == c) diag[r] else 0.0 }
        }

        fun ofRows(vararg rows: Vector): Matrix {
            return Matrix(rows.size, rows[0].size) { r, c -> rows[r][c] }
        }

        fun ofCols(vararg cols: Vector): Matrix {
            return Matrix(cols[0].size, cols.size) { r, c -> cols[c][r] }
        }
    }
    
    val grid = Array<DoubleArray>(rows) { r -> DoubleArray(cols) { c -> init(r, c) } }

    override operator fun get(r: Int, c: Int): Double {
        return grid[r][c]
    }

    override operator fun set(r: Int, c: Int, v: Double) {
        grid[r][c] = v
    }
    
    override fun toString(): String {
        val sb = StringBuilder()
        for (r in 0..<this.rows) {
            sb.append("[ ${this[r, 0]} ")
            for (c in 1..<this.cols) {
                sb.append(", ${this[r, c]} ")
            }
            sb.append("]")
            if (r != this.rows - 1) {
                sb.append("\n")
            }
        }
        return sb.toString()
    }

    override fun transpose(): Matrix {
        return Matrix(this.cols, this.rows) { r, c -> this[c, r] }
    }

    operator fun plus(m: Matrix): Matrix {
        return Matrix(this.rows, this.cols) { r, c -> this[r, c] + m[r, c] }
    }

    operator fun minus(m: Matrix): Matrix {
        return Matrix(this.rows, this.cols) { r, c -> this[r, c] - m[r, c] }
    }

    operator fun times(m: Matrix): Matrix {
        if (this.cols != m.rows) {
            throw IllegalArgumentException("Mismatched dimensions")
        }

        return Matrix(this.rows, this.cols) { r, c -> 
            (0..<this.cols).map { k -> this[r, k] * m[k, c] }.sum()
        }
    }

    operator fun times(v: Vector): Vector {
        if (this.cols != v.size) {
            throw IllegalArgumentException("Mismatched dimensions")
        }

        return Vector(this.rows) { r -> 
            (0..<this.cols).map { k -> this[r, k] * v[k] }.sum()
        }
    }

    operator fun plus(x: Number): Matrix {
        return Matrix(this.rows, this.cols) { r, c -> this[r, c] + x.toDouble() }
    }

    operator fun minus(x: Number): Matrix {
        return Matrix(this.rows, this.cols) { r, c -> this[r, c] - x.toDouble() }
    }

    operator fun times(x: Number): Matrix {
        return Matrix(this.rows, this.cols) { r, c -> this[r, c] * x.toDouble() }
    }

    operator fun div(x: Number): Matrix {
        return Matrix(this.rows, this.cols) { r, c -> this[r, c] / x.toDouble() }
    }

    operator fun rem(x: Number): Matrix {
        return Matrix(this.rows, this.cols) { r, c -> this[r, c] % x.toDouble() }
    }

    fun row(r: Int): Vector {
        return Vector(this.cols) { c -> this[r, c] }
    }

    fun col(c: Int): Vector {
        return Vector(this.rows) { r -> this[r, c] }
    }
}
