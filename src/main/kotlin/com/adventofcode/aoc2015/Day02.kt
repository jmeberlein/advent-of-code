package com.adventofcode.aoc2015

import org.openjdk.jmh.annotations.*
import com.adventofcode.util.File
import java.util.concurrent.TimeUnit

@State(Scope.Thread)
open class State02(fileName: String = "src/main/resources/input/2015/02.txt") : File(fileName)

open class Day02 {
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part1(state: State02): Long {
        var res = 0L
        for (line in state) {
            val (l, w, h) = line.split("x").map(String::toLong).sorted()
            res += l * w + 2 * (l * w + w * h + h * l)
        }
        return res
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part2(state: State02): Long {
        var res = 0L
        for (line in state) {
            val (l, w, h) = line.split("x").map(String::toLong).sorted()
            res += 2 * (l + w) + l * w * h
        }
        return res
    }

}
