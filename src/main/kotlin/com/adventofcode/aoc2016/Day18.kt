package com.adventofcode.aoc2016

import org.openjdk.jmh.annotations.*
import com.adventofcode.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.min
import kotlin.math.max

@State(Scope.Thread)
open class State18(fileName: String = "src/main/resources/input/aoc2016/18.txt") : File(fileName)

open class Day18 {
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part1(state: State18): Long {
        var res = 0L
        var grid = Grid(state[0].toInt(), state[1].length) { r, c ->
            if (r == 0) {
                state[1][c] == '^'
            } else {
                false
            }
        }
        for (r in 1..<grid.rows) {
            for (c in 0..<grid.cols) {
                val left = grid.getOrDefault(r-1, c-1, false)
                val right = grid.getOrDefault(r-1, c+1, false)
                grid[r, c] = left && !right || right && !left
            }
        }
        for (r in 0..<grid.rows) {
            for (c in 0..<grid.cols) {
                if (!grid[r, c]) {
                    res++
                }
            }
        }
        return res
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part2(state: State18): Long {
        var res = 0L
        val colsPlusBuffer = state[1].length + 2
        var curr = BooleanArray(colsPlusBuffer) { 
            if (it > 0 && it < colsPlusBuffer - 1) {
                state[1][it-1] == '^'
            } else {
                false
            } 
        }
        for (c in 1..<(colsPlusBuffer - 1)) {
            if (!curr[c]) {
                res++
            }
        }
        for (r in 1..<400000) {
            curr = BooleanArray(colsPlusBuffer) {
                if (it > 0 && it < colsPlusBuffer - 1) {
                    if (curr[it-1] && !curr[it+1] || curr[it+1] && !curr[it-1]) {
                        true
                    } else {
                        res++
                        false
                    }
                } else {
                    false
                }
            }

        }

        return res
    }
}