package com.adventofcode.aoc2015

import org.openjdk.jmh.annotations.*
import com.adventofcode.util.File
import java.util.concurrent.TimeUnit

@State(Scope.Thread)
open class State01(fileName: String = "src/main/resources/input/2015/01.txt") : File(fileName)

open class Day01 {
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part1(state: State01): Long {
        var res = 0L
        for (c in state[0]) {
            if (c == '(') {
                res++
            } else {
                res--
            }
        }
        return res
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part2(state: State01): Long {
        var res = 0L
        for ((idx, c) in state[0].withIndex()) {
            if (c == '(') {
                res++
            } else {
                res--
            }

            if (res < 0) {
                return idx + 1L
            }
        }
        return res
    }

}
