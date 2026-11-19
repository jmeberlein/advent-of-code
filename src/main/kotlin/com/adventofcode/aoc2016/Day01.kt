package com.adventofcode.aoc2016

import org.openjdk.jmh.annotations.*
import com.adventofcode.util.*
import java.util.concurrent.TimeUnit

@State(Scope.Thread)
open class State01(fileName: String = "src/main/resources/input/aoc2016/01.txt") : File(fileName)

open class Day01 {
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part1(state: State01): Long {
        var res = 0L
        var dir = Vector.UP
        var pos = Vector.of(0.0, 0.0)
        state[0].split(", ").forEach { line->
            dir *= if (line[0] == 'R') Matrix.CW else Matrix.CCW
            pos += dir * line.substring(1).toInt()
        }
        return pos.magnitude(1.0).toLong()
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part2(state: State01): Long {
        var res = 0L
        var stops = mutableListOf<Vector>()
        var dir = Vector.UP
        var pos = Vector.of(0.0, 0.0)
        outer@ for (line in state[0].split(", ")) {
            dir *= if (line[0] == 'R') Matrix.CW else Matrix.CCW
            for (i in 0..<line.substring(1).toInt()) {
                pos += dir
                if (pos in stops) {
                    break@outer
                }
                stops.add(pos)
            }
        }
        
        return pos.magnitude(1.0).toLong()
    }

}