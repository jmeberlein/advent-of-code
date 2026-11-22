package com.adventofcode.aoc2016

import org.openjdk.jmh.annotations.*
import com.adventofcode.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.min
import kotlin.math.max

@State(Scope.Thread)
open class State23(fileName: String = "src/main/resources/input/aoc2016/23.txt") : File(fileName)

open class Day23 {
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part1(state: State23): Long {
        var res = 0L
        val interpreter = Assembunny(state)
        interpreter.a = 7
        interpreter.run()
        return interpreter.a.toLong()
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part2(state: State23): Long {
        var res = 0L
        val interpreter = Assembunny(state)
        interpreter.a = 12
        interpreter.run()
        return interpreter.a.toLong()
    }
}