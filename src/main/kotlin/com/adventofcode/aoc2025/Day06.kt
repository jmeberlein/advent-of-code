package com.adventofcode.aoc2025

import org.openjdk.jmh.annotations.*
import com.adventofcode.util.File
import com.adventofcode.util.Grid
import java.util.concurrent.TimeUnit

@State(Scope.Thread)
open class State06(fileName: String = "src/main/resources/input/2025/06.txt") : File(fileName)

open class Day06 {
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part1(state: State06): Long {
        var res = 0L
        var cols = state[0].trim().split("\\s+".toRegex()).size
        var grid = Grid<Long>(state.size - 1, cols) { _, _ -> 0 }
        for (r in 0..<grid.rows) {
            val row = state[r].trim().split("\\s+".toRegex())
            for (c in 0..<grid.cols) {
                grid[r, c] = row[c].toLong()
            }
        }

        val ops = state[state.size - 1].trim().split("\\s+".toRegex())
        for (c in 0..<ops.size) {
            when (ops[c]) {
                "+" -> {
                    var curr = 0L
                    for (r in 0..<grid.rows) {
                        curr += grid[r, c]
                    }
                    res += curr
                }
                "*" -> {
                    var curr = 1L
                    for (r in 0..<grid.rows) {
                        curr *= grid[r, c]
                    }
                    res += curr
                }
            }
        }
        return res
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part2(state: State06): Long {
        var res = 0L
        val nums = (0..<state[0].length).map { c ->
            CharArray(state.size-1) { r -> state[r][c] }.concatToString().strip()
        }
        val ops = state[state.size - 1].trim().split("\\s+".toRegex())
        var op = 0
        var curr = if (ops[0] == "*") 1L else 0L
        for (num in nums) {
            if (num.isEmpty()) {
                res += curr
                curr = if (ops[++op] == "*") 1L else 0L
            } else if (ops[op] == "*") {
                curr *= num.toLong()
            } else {
                curr += num.toLong()
            }
        }
        res += curr
        return res
    }

}
