package com.adventofcode.aoc2016

import org.openjdk.jmh.annotations.*
import com.adventofcode.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.min
import kotlin.math.max

@State(Scope.Thread)
open class State12(fileName: String = "src/main/resources/input/aoc2016/12.txt") : File(fileName)

open class Day12 {
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part1(state: State12): Long {
        var res = 0L
        val registers = IntArray(4)
        var idx = 0
        while (idx < state.size) {
            val toks = state[idx].split(" ")
            val op0 = when (toks[1]) {
                "a" -> registers[0]
                "b" -> registers[1]
                "c" -> registers[2]
                "d" -> registers[3]
                else -> toks[1].toInt()
            }
            when (toks[0]) {
                "inc" -> registers[toks[1][0].code - 0x61]++
                "dec" -> registers[toks[1][0].code - 0x61]--
                "jnz" -> if (op0 != 0) idx += (toks[2].toInt() - 1)
                "cpy" -> registers[toks[2][0].code - 0x61] = op0
            }
            idx++
        }
        return registers[0].toLong()
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part2(state: State12): Long {
        var res = 0L
        val registers = IntArray(4)
        registers[2] = 1
        var idx = 0
        while (idx < state.size) {
            val toks = state[idx].split(" ")
            val op0 = when (toks[1]) {
                "a" -> registers[0]
                "b" -> registers[1]
                "c" -> registers[2]
                "d" -> registers[3]
                else -> toks[1].toInt()
            }
            when (toks[0]) {
                "inc" -> registers[toks[1][0].code - 0x61]++
                "dec" -> registers[toks[1][0].code - 0x61]--
                "jnz" -> if (op0 != 0) idx += (toks[2].toInt() - 1)
                "cpy" -> registers[toks[2][0].code - 0x61] = op0
            }
            idx++
        }
        return registers[0].toLong()
    }
}