package com.adventofcode.util

import org.apache.commons.math3.complex.Complex

class Grid<T>(rows: Int, cols: Int) {
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
}