package com.adventofcode.aoc2025

import org.openjdk.jmh.annotations.*
import java.util.concurrent.TimeUnit
import org.apache.commons.math3.complex.Complex
import com.adventofcode.util.*

@State(Scope.Thread)
open class State04(fileName: String = "src/main/resources/input/aoc2025/04.txt") : File(fileName) {
    var grid: Grid<Boolean>

    init {
        this.grid = Grid<Boolean>(this.size, this[0].length) { r, c -> this[r][c] == '@' }
    }
}

open class Day04 {
    fun part1(state: File): Long {
        return part1(state as State04)
    }

    fun part2(state: File): Long {
        return part2(state as State04)
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part1(state: State04): Long {
        var res = 0L
        for (r in 0..<state.grid.rows) {
            for (c in 0..<state.grid.cols) {
                val p = IntVector.of(r, c)
                if (state.grid[p] && countAdjacencies(state.grid, p) < 4) {
                    res++
                }
            }
        }
        return res
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part2(state: State04): Long {
        var res = 0L
        var curr: Long
        do {
            curr = 0L
            var toRemove = ArrayList<IntVector>()
            for (r in 0..<state.grid.rows) {
                for (c in 0..<state.grid.cols) {
                    val p = IntVector.of(r, c)
                    if (state.grid[p] && countAdjacencies(state.grid, p) < 4) {
                        toRemove.add(p)
                        curr++
                    }
                }
            }

            for (p in toRemove) {
                state.grid[p] = false
            }

            res += curr
        } while (curr != 0L)
        return res
    }

    fun countAdjacencies(grid: Grid<Boolean>, p: IntVector): Int {
        val ADJACENCIES: Array<IntVector> = arrayOf(
            IntVector.of(-1, -1), IntVector.of(-1,  0), IntVector.of(-1,  1),
            IntVector.of( 0, -1),                       IntVector.of( 0,  1),
            IntVector.of( 1, -1), IntVector.of( 1,  0), IntVector.of( 1,  1)
        )

        var adj = 0
        for (offset: IntVector in ADJACENCIES) {
            if (grid.getOrDefault(offset + p, false)) {
                adj++
            }
        }
        return adj
    }
}
