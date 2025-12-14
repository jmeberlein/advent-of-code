package com.adventofcode.aoc2015

import org.openjdk.jmh.annotations.*
import com.adventofcode.util.File
import com.adventofcode.util.IntVector
import java.util.concurrent.TimeUnit

@State(Scope.Thread)
open class State03(fileName: String = "src/main/resources/input/2015/03.txt") : File(fileName)

open class Day03 {
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part1(state: State03): Long {
        var res = 0L
        var santa = IntVector.of(0, 0)
        val visited = mutableSetOf(santa)
        for (c in state[0]) {
            santa += when (c) {
                '^' -> IntVector.UP
                'v' -> IntVector.DOWN
                '<' -> IntVector.LEFT
                '>' -> IntVector.RIGHT
                else -> IntVector.of(0, 0)
            }
            visited.add(santa)
        }
        return visited.size.toLong()
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part2(state: State03): Long {
        var res = 0L
        var santa = IntVector.of(0, 0)
        var roboSanta = IntVector.of(0, 0)
        val visited = mutableSetOf(santa)
        for ((idx, c) in state[0].withIndex()) {
            val adj = when (c) {
                '^' -> IntVector.UP
                'v' -> IntVector.DOWN
                '<' -> IntVector.LEFT
                '>' -> IntVector.RIGHT
                else -> IntVector.of(0, 0)
            }

            if (idx % 2 == 0) {
                santa += adj
                visited.add(santa)
            } else {
                roboSanta += adj
                visited.add(roboSanta)
            }
        }
        return visited.size.toLong()
    }

}
