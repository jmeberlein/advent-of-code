package com.adventofcode.aoc2015

import org.openjdk.jmh.annotations.*
import com.adventofcode.util.File
import java.util.concurrent.TimeUnit

@State(Scope.Thread)
open class State10(fileName: String = "src/main/resources/input/2015/10.txt") : File(fileName)

open class Day10 {
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part1(state: State10): Long {
        var res = 0L
        var s = state[0]
        for (i in 0..<40) {
            s = lookAndSay(s)
        }
        return s.length.toLong()
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part2(state: State10): Long {
        var res = 0L
        var s = state[0]
        for (i in 0..<50) {
            s = lookAndSay(s)
        }
        return s.length.toLong()
    }

    fun lookAndSay(s: String): String {
        val sb = StringBuilder()
        var lt = 0
        var rt = 0
        while (lt < s.length) {
            while (rt < s.length && s[lt] == s[rt]) {
                rt++
            }
            sb.append(rt - lt)
            sb.append(s[lt])
            lt = rt
        }
        return sb.toString()
    }
}
