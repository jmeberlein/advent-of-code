package com.adventofcode.util

import org.apache.commons.math3.complex.Complex

class Grid<T>(val rows: Int, val cols: Int) {
    val grid = Array(rows) { arrayOfNulls<Any?>(cols) }

    operator fun <T> get(i: Complex): T {
        return grid[i.getReal().toInt()][i.getImaginary().toInt()] as T
    }

    operator fun <T> set(i: Complex, v: T) {
        grid[i.getReal().toInt()][i.getImaginary().toInt()] = v
    }
    
    fun <T> getOrDefault(i: Complex, default: T): T {
        try {
            val tmp = grid[i.getReal().toInt()][i.getImaginary().toInt()] as T?
            return tmp ?: default
        } catch (e: IndexOutOfBoundsException) {
            return default
        }
    }

    operator fun <T> get(r: Int, c: Int): T {
        return grid[r][c] as T
    }

    operator fun <T> set(r: Int, c: Int, v: T) {
        grid[r][c] = v
    }
    
    fun <T> getOrDefault(r: Int, c: Int, default: T): T {
        try {
            val tmp = grid[r][c] as T?
            return tmp ?: default
        } catch (e: IndexOutOfBoundsException) {
            return default
        }
    }
}
