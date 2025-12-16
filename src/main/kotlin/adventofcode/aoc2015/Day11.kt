package com.adventofcode.aoc2015

import org.openjdk.jmh.annotations.*
import com.adventofcode.util.File
import java.util.concurrent.TimeUnit

@State(Scope.Thread)
open class State11(fileName: String = "src/main/resources/input/2015/11.txt") : File(fileName)

open class Day11 {
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part1(state: State11): String {
        var res = state[0]
        while (!isValid(res)) {
            res = nextPassword(res)
        }
        return res
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part2(state: State11): String {
        var res = state[0]
        while (!isValid(res)) {
            res = nextPassword(res)
        }
        res = nextPassword(res)
        while (!isValid(res)) {
            res = nextPassword(res)
        }
        return res
    }

    fun nextPassword(s: String): String {
        var char = s.toCharArray()
        for (i in (s.length-1).downTo(0)) {
            var c = char[i].code + 1
            if (c > 0x60 + 26) {
                c -= 26
                char[i] = c.toChar()
            } else {
                char[i] = c.toChar()
                break
            }
        }
        return char.joinToString("")
    }

    fun isValid(s: String): Boolean {
        if (s.contains('i') || s.contains('l') || s.contains('o')) {
            return false
        }

        if (Regex("(.)\\1").findAll(s).map { it.groupValues[0] }.distinct().count() < 2) {
            return false
        }

        for (i in 2..<s.length) {
            if (s[i-2].code + 1 == s[i-1].code && s[i-1].code + 1 == s[i].code) {
                return true
            }
        }

        return false
    }
}
