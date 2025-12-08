package com.adventofcode.aoc2025

import org.openjdk.jmh.annotations.*
import com.adventofcode.util.File
import java.util.concurrent.TimeUnit

@State(Scope.Thread)
open class State01(fileName: String = "src/main/resources/input/2025/01.txt") : File(fileName)

open class Day01 {
    fun part1(state: File): Long {
        return part1(state as State01)
    }

    fun part2(state: File): Long {
        return part2(state as State01)
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part1(state: State01): Long {
        var res = 0L
        var pos = 50
        for (line in state) {
            var offset = line.substring(1).toInt()
            offset %= 100

            if (line[0] == 'R') {
                pos += offset
            } else {
                pos -= offset
            }

            pos %= 100

            if (pos == 0) {
                res++
            }
        }
        return res
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part2(state: State01): Long {
        var res = 0L
        var pos = 50
        for (line in state) {
            var offset = line.substring(1).toInt()
            res += offset / 100
            offset %= 100

            if (line[0] == 'R') {
                pos += offset
            } else {
                pos -= offset
            }

            if (pos == 0) {
                res++
            } else if (pos >= 100) {
                res++
                pos -= 100
            } else if (pos < 0) {
                if (pos + offset != 0) {
                    res++
                }
                pos += 100
            }
        }
        return res
    }

}
