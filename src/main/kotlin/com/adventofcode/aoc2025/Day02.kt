package com.adventofcode.aoc2025

import org.openjdk.jmh.annotations.*
import com.adventofcode.util.File
import java.util.concurrent.TimeUnit

@State(Scope.Thread)
open class State02(fileName: String = "src/main/resources/input/2025/02.txt") : File(fileName)

open class Day02 {
    fun part1(state: File): Long {
        return part1(state as State02)
    }

    fun part2(state: File): Long {
        return part2(state as State02)
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part1(state: State02): Long {
        var sum = 0L;
        for (range in state[0].split(",")) {
            var start = range.split("-")[0].toLong()
            var end = range.split("-")[1].toLong() + 1
            for (i in start..<end) {
                if (verifyCode(i.toString())) {
                    sum += i
                }
            }
        }
        return sum
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part2(state: State02): Long {
        var sum = 0L;
        for (range in state[0].split(",")) {
            var start = range.split("-")[0].toLong()
            var end = range.split("-")[1].toLong() + 1
            for (i in start..<end) {
                if (verifyCodeFull(i.toString())) {
                    sum += i
                }
            }
        }
        return sum
    }

    fun verifyCode(s: String): Boolean {
        if (s.length % 2 != 0) {
            return false
        }

        val substrLength = s.length / 2
        for (i in 0..<substrLength) {
            if (s[i] != s[i+substrLength]) {
                return false
            }
        }

        return true
    }

    fun verifyCodeFull(s: String): Boolean {
        substrCount@for(substrCount in 2..s.length) {
            if (s.length % substrCount != 0) {
                continue
            }

            val substrLength = s.length / substrCount
            for (i in 0..<substrLength) {
                for (j in 1..<substrCount) {
                    if (s[i] != s[i+j*substrLength]) {
                        continue@substrCount
                    }
                }
            }

            return true
        }

        return false
    }
}
