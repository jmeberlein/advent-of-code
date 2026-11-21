package com.adventofcode.aoc2016

import org.openjdk.jmh.annotations.*
import com.adventofcode.util.*
import java.util.concurrent.TimeUnit

@State(Scope.Thread)
open class State05(fileName: String = "src/main/resources/input/aoc2016/05.txt") : File(fileName)

open class Day05 {
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part1(state: State05): Long {
        var res = ""
        var salt = 0
        while (res.length < 8) {
            val md5 = (state[0] + salt).md5()
            if (md5.substring(0, 5) == "00000") {
                res += md5[5]
            }
            salt++
        }
        println(res)
        return 0L
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part2(state: State05): Long {
        var res = CharArray(8) { 'x' }
        var salt = 0
        while (res.any { it == 'x' }) {
            val md5 = (state[0] + salt).md5()
            if (md5.substring(0, 5) == "00000") {
                if (md5[5].code < 0x38 && res[md5[5].code - 0x30] == 'x') {
                    res[md5[5].code - 0x30] = md5[6]
                }
            }
            salt++
        }
        println(res.concatToString())
        return 0L
    }

}