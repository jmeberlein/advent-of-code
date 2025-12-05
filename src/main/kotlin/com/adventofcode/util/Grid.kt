package com.adventofcode.util

import org.apache.commons.math3.complex.Complex

class Grid<T>(val rows: Int, val cols: Int, fill: T) {
    val grid: MutableList<MutableList<T>> = MutableList(rows) { MutableList(cols) { fill } }

    operator fun get(i: Complex): T {
        return grid[i.real.toInt()][i.imaginary.toInt()]
    }

    operator fun set(i: Complex, v: T) {
        grid[i.real.toInt()][i.imaginary.toInt()] = v
    }
    
    fun getOrDefault(i: Complex, default: T): T {
        try {
            return grid[i.real.toInt()][i.imaginary.toInt()]
        } catch (e: IndexOutOfBoundsException) {
            return default
        }
    }

    operator fun get(r: Int, c: Int): T {
        return grid[r][c]
    }

    operator fun set(r: Int, c: Int, v: T) {
        grid[r][c] = v
    }
    
    fun getOrDefault(r: Int, c: Int, default: T): T {
        try {
            return grid[r][c]
        } catch (e: IndexOutOfBoundsException) {
            return default
        }
    }
}
