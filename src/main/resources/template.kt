package com.adventofcode.aoc{yyyy}

import org.openjdk.jmh.annotations.*
import com.adventofcode.util.File
import java.util.concurrent.TimeUnit

@State(Scope.Thread)
open class State{dd}(fileName: String = "src/main/resources/input/{yyyy}/{dd}.txt") : File(fileName)

open class Day{dd} {
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part1(state: State{dd}): Long {
        var res = 0L

        return res
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part2(state: State{dd}): Long {
        var res = 0L

        return res
    }

}
