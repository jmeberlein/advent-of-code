package com.adventofcode.aoc2016

import org.openjdk.jmh.annotations.*
import com.adventofcode.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.min
import kotlin.math.max

@State(Scope.Thread)
open class State19(fileName: String = "src/main/resources/input/aoc2016/19.txt") : File(fileName)

open class Day19 {
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part1(state: State19): Long {
        var res = 0L
        val elves = state[0].toInt()
        return 2L * (elves - elves.floorPowerOfTwo()) + 1
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part2(state: State19): Long {
        var res = 0L
        val elves = state[0].toLong()
        var floorPowerOfThree = 1
        while (3 * floorPowerOfThree < elves) {
            floorPowerOfThree *= 3
        }
        return elves - floorPowerOfThree + max(0, elves - 2*floorPowerOfThree)
    }
}