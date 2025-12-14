package com.adventofcode.util

interface Array2D<T> {
    val rows: Int
    val cols: Int

    operator fun get(r: Int, c: Int): T

    operator fun get(vec: IntVector): T {
        if (vec.size != 2) {
            throw IllegalArgumentException("Index must have 2 elements")
        }

        return this[vec[0].toInt(), vec[1].toInt()]
    }

    operator fun set(r: Int, c: Int, v: T)

    operator fun set(vec: IntVector, v: T) {
        if (vec.size != 2) {
            throw IllegalArgumentException("Index must have 2 elements")
        }

        this[vec[0].toInt(), vec[1].toInt()] = v
    }

    fun transpose(): Array2D<T>
}

class Grid<T>(override val rows: Int, override val cols: Int = rows, init: (Int, Int) -> T): Array2D<T> {
    val grid = List(rows) { r -> MutableList(cols) { c -> init(r, c) } }

    override operator fun get(r: Int, c: Int): T {
        return grid[r][c]
    }

    override operator fun set(r: Int, c: Int, v: T) {
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
    
    fun getOrDefault(r: Int, c: Int, default: T): T {
        try {
            return grid[r][c]
        } catch (e: IndexOutOfBoundsException) {
            return default
        }
    }
    
    fun getOrDefault(vec: IntVector, default: T): T {
        try {
            return this[vec]
        } catch (e: IndexOutOfBoundsException) {
            return default
        } catch (e: IllegalArgumentException) {
            return default
        }
    }

    override fun transpose(): Grid<T> {
        return Grid(this.cols, this.rows) { r, c -> this[c, r] }
    }
}
