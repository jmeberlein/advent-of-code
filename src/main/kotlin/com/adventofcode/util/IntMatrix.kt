package com.adventofcode.util

class IntMatrix(override val rows: Int, override val cols: Int = rows, init: (Int, Int) -> Int): Array2D<Int> {
    companion object {
        fun identity(n: Int): IntMatrix {
            return IntMatrix(n) { r, c -> if (r == c) 1 else 0 }
        }

        fun scalar(n: Int, value: Int): IntMatrix {
            return IntMatrix(n) { r, c -> if (r == c) value else 0 }
        }

        fun diagonal(vararg diag: Int): IntMatrix {
            return IntMatrix(diag.size) { r, c -> if (r == c) diag[r] else 0 }
        }

        fun ofRows(vararg rows: IntVector): IntMatrix {
            return IntMatrix(rows.size, rows[0].size) { r, c -> rows[r][c] }
        }

        fun ofCols(vararg cols: IntVector): IntMatrix {
            return IntMatrix(cols[0].size, cols.size) { r, c -> cols[c][r] }
        }
    }
    
    val grid = Array<IntArray>(rows) { r -> IntArray(cols) { c -> init(r, c) } }

    override operator fun get(r: Int, c: Int): Int {
        return grid[r][c]
    }

    override operator fun set(r: Int, c: Int, v: Int) {
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

    override fun transpose(): IntMatrix {
        return IntMatrix(this.cols, this.rows) { r, c -> this[c, r] }
    }

    operator fun plus(m: IntMatrix): IntMatrix {
        return IntMatrix(this.rows, this.cols) { r, c -> this[r, c] + m[r, c] }
    }

    operator fun minus(m: IntMatrix): IntMatrix {
        return IntMatrix(this.rows, this.cols) { r, c -> this[r, c] - m[r, c] }
    }

    operator fun times(m: IntMatrix): IntMatrix {
        if (this.cols != m.rows) {
            throw IllegalArgumentException("Mismatched dimensions")
        }

        return IntMatrix(this.rows, this.cols) { r, c -> 
            (0..<this.cols).map { k -> this[r, k] * m[k, c] }.sum()
        }
    }

    operator fun times(v: IntVector): IntVector {
        if (this.cols != v.size) {
            throw IllegalArgumentException("Mismatched dimensions")
        }

        return IntVector(this.rows) { r -> 
            (0..<this.cols).map { k -> this[r, k] * v[k] }.sum()
        }
    }

    operator fun plus(x: Int): IntMatrix {
        return IntMatrix(this.rows, this.cols) { r, c -> this[r, c] + x }
    }

    operator fun minus(x: Int): IntMatrix {
        return IntMatrix(this.rows, this.cols) { r, c -> this[r, c] - x }
    }

    operator fun times(x: Int): IntMatrix {
        return IntMatrix(this.rows, this.cols) { r, c -> this[r, c] * x }
    }

    operator fun div(x: Int): IntMatrix {
        return IntMatrix(this.rows, this.cols) { r, c -> this[r, c] / x }
    }

    operator fun rem(x: Int): IntMatrix {
        return IntMatrix(this.rows, this.cols) { r, c -> this[r, c] % x }
    }

    fun row(r: Int): IntVector {
        return IntVector(this.cols) { c -> this[r, c] }
    }

    fun col(c: Int): IntVector {
        return IntVector(this.rows) { r -> this[r, c] }
    }
}
