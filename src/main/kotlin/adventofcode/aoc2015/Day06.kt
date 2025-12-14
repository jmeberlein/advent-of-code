package com.adventofcode.aoc2015

import org.openjdk.jmh.annotations.*
import com.adventofcode.util.File
import com.adventofcode.util.Grid
import java.util.concurrent.TimeUnit

@State(Scope.Thread)
open class State06(fileName: String = "src/main/resources/input/2015/06.txt") : File(fileName)

open class Day06 {
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part1(state: State06): Long {
        var res = 0L
        var grid = Grid(1000) { _,_ -> false }
        for (line in state) {
            val toks = line.split(" ")
            if (line.startsWith("turn")) {
                val (minR, minC) = toks[2].split(",").map(String::toInt)
                val (maxR, maxC) = toks[4].split(",").map(String::toInt)
                val newState = toks[1] == "on"
                for (r in minR..maxR) {
                    for (c in minC..maxC) {
                        grid[r, c] = newState
                    }
                }
            } else {
                val (minR, minC) = toks[1].split(",").map(String::toInt)
                val (maxR, maxC) = toks[3].split(",").map(String::toInt)
                for (r in minR..maxR) {
                    for (c in minC..maxC) {
                        grid[r, c] = !grid[r,c]
                    }
                }
            }
        }
        for (r in 0..<1000) {
            for (c in 0..<1000) {
                if (grid[r, c]) {
                    res++
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
        var grid = Grid(1000) { _,_ -> 0 }
        for (line in state) {
            val toks = line.split(" ")
            if (line.startsWith("turn")) {
                val (minR, minC) = toks[2].split(",").map(String::toInt)
                val (maxR, maxC) = toks[4].split(",").map(String::toInt)
                val adj = if (toks[1] == "on") 1 else -1
                for (r in minR..maxR) {
                    for (c in minC..maxC) {
                        grid[r, c] += adj
                        if (grid[r, c] < 0) {
                            grid[r, c] = 0
                        }
                    }
                }
            } else {
                val (minR, minC) = toks[1].split(",").map(String::toInt)
                val (maxR, maxC) = toks[3].split(",").map(String::toInt)
                for (r in minR..maxR) {
                    for (c in minC..maxC) {
                        grid[r, c] += 2
                    }
                }
            }
        }
        for (r in 0..<1000) {
            for (c in 0..<1000) {
                res += grid[r, c]
            }
        }
        return res
    }

}
