package com.adventofcode.aoc2025

import org.openjdk.jmh.annotations.*
import com.adventofcode.util.File
import java.util.concurrent.TimeUnit

@State(Scope.Thread)
open class State03(fileName: String = "src/main/resources/input/2025/03.txt") : File(fileName)

open class Day03 {
    fun part1(state: File): Long {
        return part1(state as State03)
    }

    fun part2(state: File): Long {
        return part2(state as State03)
    }

    @Benchmark
    @BenchmarkMode(Mode.SampleTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part1(state: State03): Long {
        var res = 0L
        for (line in state) {
            res += maxJoltage(line, 2)
        }
        return res
    }

    @Benchmark
    @BenchmarkMode(Mode.SampleTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part2(state: State03): Long {
        var res = 0L
        for (line in state) {
            res += maxJoltage(line, 12)
        }
        return res
    }

    fun maxJoltage(batteries: String, num: Int): Long {
        var maxJoltage = 0L
        var maxIdx = -1

        for (i in 0..<num) {
            maxIdx++
            var max = batteries[maxIdx]
            val offset = num - i - 1
            for (j in (maxIdx+1)..<(batteries.length - offset)) {
                if (batteries[j] > max) {
                    max = batteries[j]
                    maxIdx = j
                }
            }
            maxJoltage = 10 * maxJoltage + (max - '0')
        }
        
        return maxJoltage
    }

}
