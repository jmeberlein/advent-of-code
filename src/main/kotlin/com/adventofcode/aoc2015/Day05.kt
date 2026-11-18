package com.adventofcode.aoc2015

import org.openjdk.jmh.annotations.*
import com.adventofcode.util.File
import java.util.concurrent.TimeUnit

@State(Scope.Thread)
open class State05(fileName: String = "src/main/resources/input/2015/05.txt") : File(fileName)

open class Day05 {
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part1(state: State05): Long {
        // var res = 0L
        // grep ".*[aeiou].*[aeiou].*[aeiou].*" src/main/resources/input/2015/05.txt | grep -Ev "ab|cd|pq|xy" | grep -E "(.)\1" | wc -l
        return state.count { line -> isNice(line) }.toLong()
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part2(state: State05): Long {
        // var res = 0L
        val rgxA = Regex("(.).\\1")
        val rgxB = Regex("(.{2}).*\\1")
        return state.count { s -> s.contains(rgxA) && s.contains(rgxB) }.toLong()
    }

    fun isNice(s: String): Boolean {
        var vowels = 0
        var hasDoubleLetter = false
        for ((i, c) in s.withIndex()) {
            if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
                vowels++
            }

            if (i != 0) {
                if (s[i] == s[i-1]) {
                    hasDoubleLetter = true
                }
                if (s[i] == 'b' && s[i-1] == 'a') {
                    return false
                }
                if (s[i] == 'd' && s[i-1] == 'c') {
                    return false
                }
                if (s[i] == 'q' && s[i-1] == 'p') {
                    return false
                }
                if (s[i] == 'y' && s[i-1] == 'x') {
                    return false
                }
            }
        }

        return hasDoubleLetter && vowels >= 3
    }
}
