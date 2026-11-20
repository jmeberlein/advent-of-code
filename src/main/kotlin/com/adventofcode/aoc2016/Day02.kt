package com.adventofcode.aoc2016

import org.openjdk.jmh.annotations.*
import com.adventofcode.util.*
import java.util.concurrent.TimeUnit

@State(Scope.Thread)
open class State02(fileName: String = "src/main/resources/input/aoc2016/02.txt") : File(fileName) {
    val keypad02 = Grid.ofRows(
        arrayOf('X', 'X', '1', 'X', 'X'),
        arrayOf('X', '2', '3', '4', 'X'),
        arrayOf('5', '6', '7', '8', '9'),
        arrayOf('X', 'A', 'B', 'C', 'X'),
        arrayOf('X', 'X', 'D', 'X', 'X'),
    )
}

open class Day02 {

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part1(state: State02): Long {
        var res = 0L
        var key = 4
        for (line in state) {
            for (c in line) {
                when (c) {
                    'U' -> {
                        if (key / 3 > 0) {
                            key -= 3
                        }
                    }
                    'D' -> {
                        if (key / 3 < 2) {
                            key += 3
                        }
                    }
                    'L' -> {
                        if (key % 3 > 0) {
                            key -= 1
                        }
                    }
                    'R' -> {
                        if (key % 3 < 2) {
                            key += 1
                        }
                    }
                }
            }
            res *= 10
            // The logic's cleaner if you shift everything down by 1
            res += key + 1L
        }
        return res
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part2(state: State02): Long {
        var res = ""
        var pos = IntVector.of(3, 0)
        for (line in state) {
            for (c in line) {
                val newPos = pos + when(c) {
                    'U' -> IntVector.UP
                    'D' -> IntVector.DOWN
                    'L' -> IntVector.LEFT
                    'R' -> IntVector.RIGHT
                    else -> IntVector.UP
                }
                if (newPos[0] >= 0 && newPos[0] < 5 && newPos[1] >= 0 && newPos[1] < 5 && state.keypad02[newPos] != 'X') {
                    pos = newPos
                }
            }
            res += state.keypad02[pos]
        }
        println(res)
        return 0L
    }

}