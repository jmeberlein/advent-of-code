package com.adventofcode.aoc2015

import org.openjdk.jmh.annotations.*
import com.adventofcode.util.File
import java.util.concurrent.TimeUnit

@State(Scope.Thread)
open class State08(fileName: String = "src/main/resources/input/2015/08.txt") : File(fileName)

open class Day08 {
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part1(state: State08): Long {
        var res = 0L
        for (line in state) {
            res += 2
            var i = 1
            while (i < line.lastIndex - 1) {
                if (line[i] == '\\') {
                    if (line[i+1] == 'x') {
                        res += 2
                        i += 2
                    }
                    res += 1
                    i += 1
                }
                i++
            }
        }
        return res
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part2(state: State08): Long {
        var res = 0L
        for (line in state) {
            res += 2 + line.count { c -> c == '"' || c == '\\' }
        }
        return res
    }

}
