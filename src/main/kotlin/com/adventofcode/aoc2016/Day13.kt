package com.adventofcode.aoc2016

import org.openjdk.jmh.annotations.*
import com.adventofcode.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.min
import kotlin.math.max

@State(Scope.Thread)
open class State13(fileName: String = "src/main/resources/input/aoc2016/13.txt") : File(fileName)

open class Day13 {
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part1(state: State13): Long {
        var res = 0L
        val num = state[0].toInt()
        val grid = LPAGrid(50, 50, IntVector.of(1, 1), IntVector.of(39, 31)) { r, c->
            (c*c + 3*c + 2*r*c + r + r*r + num).countOneBits() % 2 == 1
        }
        return grid.reported[grid.dest].toLong()
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part2(state: State13): Long {
        var res = 0L
        val num = state[0].toInt()
        val grid = LPAGrid(50, 50, IntVector.of(1, 1), IntVector.of(39, 31)) { r, c->
            (c*c + 3*c + 2*r*c + r + r*r + num).countOneBits() % 2 == 1
        }
        for (r in 0..<50) {
            for (c in 0..<50) {
                if (grid.reported[r, c] <= 50) {
                    res++
                }
            }
        }
        return res
    }
}