package com.adventofcode.aoc2016

import org.openjdk.jmh.annotations.*
import com.adventofcode.util.*
import java.util.concurrent.TimeUnit

@State(Scope.Thread)
open class State06(fileName: String = "src/main/resources/input/aoc2016/06.txt") : File(fileName)

open class Day06 {
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part1(state: State06): Long {
        var res = ""
        var len = state[0].length
        var counts = IntMatrix(len, 26) { r, c -> 0 }
        for (line in state) {
            for (i in 0..<len) {
                counts[i, line[i].code - 0x61]++
            }
        }

        for (i in 0..<len) {
            var maxIdx = 0
            var maxCnt = counts[i, 0]
            for (j in 1..<26) {
                if (counts[i, j] > maxCnt) {
                    maxIdx = j
                    maxCnt = counts[i, j]
                }
            }
            res += (maxIdx + 0x61).toChar()
        }

        println(res)
        return 0L
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part2(state: State06): Long {
        var res = ""
        var len = state[0].length
        var counts = IntMatrix(len, 26) { r, c -> 0 }
        for (line in state) {
            for (i in 0..<len) {
                counts[i, line[i].code - 0x61]++
            }
        }

        for (i in 0..<len) {
            var minIdx = -1
            var minCnt = state.size
            for (j in 0..<26) {
                if (counts[i, j] < minCnt && counts[i, j] != 0) {
                    minIdx = j
                    minCnt = counts[i, j]
                }
            }
            res += (minIdx + 0x61).toChar()
        }

        println(res)
        return 0L
    }

}