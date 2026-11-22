package com.adventofcode.aoc2016

import org.openjdk.jmh.annotations.*
import com.adventofcode.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.min
import kotlin.math.max

@State(Scope.Thread)
open class State15(fileName: String = "src/main/resources/input/aoc2016/15.txt") : File(fileName)

open class Day15 {
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part1(state: State15): Long {
        var res = 0L
        val moduli = IntVector(state.size) { state[it].split(" ")[3].toInt() }
        val positions = IntVector(state.size) {
            (-(state[it].split(" ")[11].dropLast(1).toInt() + it + 1)).mod(moduli[it])
        }
        return positions.remainder(moduli).toLong()
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part2(state: State15): Long {
        var res = 0L
        val moduli = IntVector(state.size + 1) { if (it < state.size) state[it].split(" ")[3].toInt() else 11 }
        val positions = IntVector(state.size + 1) {
            (-((if (it < state.size) state[it].split(" ")[11].dropLast(1).toInt() else 0) + it + 1)).mod(moduli[it])
        }
        return positions.remainder(moduli).toLong()
    }
}