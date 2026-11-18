package com.adventofcode.aoc2025

import org.openjdk.jmh.annotations.*
import com.adventofcode.util.File
import com.adventofcode.util.IntVector
import com.adventofcode.util.IntMatrix
import java.util.concurrent.TimeUnit
import kotlin.math.max
import kotlin.math.min
import kotlin.math.abs

@State(Scope.Thread)
open class State12(fileName: String = "src/main/resources/input/2025/12.txt") : File(fileName) {}

open class Day12 {
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part1(state: State12): Long {
        var res = 0L
        val one = IntVector(3) { _ -> 0 }
        val patterns = mutableListOf<IntMatrix>()
        var idx = 0
        while (state[idx][1] != 'x') {
            val pattern = IntMatrix(3) { r, c -> if (state[idx+r+1][c] == '#') 1 else 0 }
            patterns.add(pattern)
            idx += 5
        }
        res = (idx..<state.size).count { line->
            val toks = state[line].split(" ")
            val dim = toks[0].split(Regex("x|:"))
            val area = dim[0].toInt() * dim[1].toInt()
            val presents = (0..<patterns.size).map { i->
                toks[i+1].toInt() * (patterns[i] * one * one)
            }.sum()
            presents <= area
        }.toLong()
        return res
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part2(state: State12): Long {
        var res = 0L
        return res
    }

}
