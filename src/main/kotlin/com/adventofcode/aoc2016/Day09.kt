package com.adventofcode.aoc2016

import org.openjdk.jmh.annotations.*
import com.adventofcode.util.*
import java.util.concurrent.TimeUnit

@State(Scope.Thread)
open class State09(fileName: String = "src/main/resources/input/aoc2016/09.txt") : File(fileName)

open class Day09 {
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part1(state: State09): Long {
        var res = 0L
        for (line in state) {
            println(decompress(line))
        }
        return res
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part2(state: State09): Long {
        var res = 0L
        for (line in state) {
            println(decompress(line, true))
        }
        return res
    }

    fun decompress(line: String, recurse: Boolean = false): Long {
        var res = 0L
        var hasParenthesis = false
        var prev = 0
        var curr = 0
        while (curr < line.length) {
            if (hasParenthesis) {
                if (line[curr] == ')') {
                    val toks = line.substring(prev+1, curr).split('x')
                    if (recurse) {
                        res += toks[1].toLong() * decompress(line.substring(curr + 1, curr + 1 + toks[0].toInt()), true)
                    } else {
                        res += toks[0].toLong() * toks[1].toLong()
                    }
                    curr += toks[0].toInt()
                    hasParenthesis = false
                }
            } else {
                if (line[curr] == '(') {
                    hasParenthesis = true
                    prev = curr
                } else {
                    res++
                }
            }

            curr++
        }
        return res
    }
}