package com.adventofcode.aoc2016

import org.openjdk.jmh.annotations.*
import com.adventofcode.util.*
import java.util.concurrent.TimeUnit

@State(Scope.Thread)
open class State03(fileName: String = "src/main/resources/input/aoc2016/03.txt") : File(fileName)

open class Day03 {
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part1(state: State03): Long {
        var res = 0L
        for (line in state) {
            val (a, b, c) = line.trim().split(Regex("\\s+")).map { it.toInt() }
            if (a + b > c && a + c > b && b + c > a) {
                res++
            }
        }
        return res
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part2(state: State03): Long {
        var res = 0L
        for (i in 0..<state.size step 3) {
            val (a1, a2, a3) = state[i].trim().split(Regex("\\s+")).map { it.toInt() }
            val (b1, b2, b3) = state[i+1].trim().split(Regex("\\s+")).map { it.toInt() }
            val (c1, c2, c3) = state[i+2].trim().split(Regex("\\s+")).map { it.toInt() }
            if (a1 + b1 > c1 && a1 + c1 > b1 && b1 + c1 > a1) {
                res++
            }
            if (a2 + b2 > c2 && a2 + c2 > b2 && b2 + c2 > a2) {
                res++
            }
            if (a3 + b3 > c3 && a3 + c3 > b3 && b3 + c3 > a3) {
                res++
            }
        }
        return res
    }

}