package com.adventofcode.aoc2025

import org.openjdk.jmh.annotations.*
import com.adventofcode.util.File
import com.adventofcode.util.Grid
import java.util.concurrent.TimeUnit

@State(Scope.Thread)
open class State07(fileName: String = "src/main/resources/input/2025/07.txt") : File(fileName) {
    val grid = Grid<Char>(this.size, this[0].length) { r, c -> this[r][c] }
}

open class Day07 {
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part1(state: State07): Long {
        var res = 0L
        for (c in 0..<state.grid.cols) {
            var hasSplitter = false
            var r = state.grid.rows - 1
            while (r >= 0 && state.grid[r, c] != '^') {
                r--
            }
            while (r >= 0) {
                when (state.grid[r, c]) {
                    '^' -> {
                        if (hasSplitter) {
                            res++
                        }
                        hasSplitter = false
                    }
                    'S' -> {
                        res++
                        hasSplitter = false
                    }
                    '.' -> {
                        if ((c > 0 && state.grid[r, c-1] == '^') || (c < state.grid.cols - 1 && state.grid[r, c+1] == '^')) {
                            hasSplitter = true
                        }
                    }
                }
                r--
            }

            if (hasSplitter) {
                res++
            }
        }
        return res
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part2(state: State07): Long {
        var res = 0L
        val timelines = Grid<Long>(state.grid.rows, state.grid.cols) { r, c -> if (state.grid[r, c] == 'S') 1L else 0L }
        for (r in 1..<timelines.rows) {
            for (c in 0..<timelines.cols) {
                if (state.grid[r, c] == '^') {
                    timelines[r, c] = timelines[r-1, c]
                } else if (state.grid[r, c] == '.') {
                    if (state.grid[r-1, c] != '^') {
                        timelines[r, c] = timelines[r-1, c]
                    }
                    if (c > 0 && state.grid[r, c-1] == '^') {
                        timelines[r, c] += timelines[r-1, c-1]
                    }
                    if (c < state.grid.cols - 1 && state.grid[r, c+1] == '^') {
                        timelines[r, c] += timelines[r-1, c+1]
                    }
                }
            }
        }
        for (c in 0..<timelines.cols) {
            res += timelines[timelines.rows - 1, c]
        }
        return res
    }

}
