package com.adventofcode.aoc2016

import org.openjdk.jmh.annotations.*
import com.adventofcode.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.min
import kotlin.math.max

@State(Scope.Thread)
open class State17(fileName: String = "src/main/resources/input/aoc2016/17.txt") : File(fileName)

open class Day17 {
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part1(state: State17): Long {
        var res = 0L
        val queue = ArrayDeque<Pair<IntVector, String>>()
        queue.addLast(Pair(IntVector.of(0, 0), ""))
        while (queue.size > 0) {
            val (pos, path) = queue.removeFirst()
            if (pos[0] == 3 && pos[1] == 3) {
                println(path)
                break
            }

            for (dir in getOpenDoors(state[0], pos, path)) {
                val nextPath = path + dir
                val nextPos = pos + when (dir) {
                    "U" -> IntVector.UP
                    "D" -> IntVector.DOWN
                    "L" -> IntVector.LEFT
                    else -> IntVector.RIGHT
                }
                queue.addLast(Pair(nextPos, nextPath))
            }
        }
        return res
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part2(state: State17): Long {
        var res = 0L
        val queue = ArrayDeque<Pair<IntVector, String>>()
        queue.addLast(Pair(IntVector.of(0, 0), ""))
        while (queue.size > 0) {
            val (pos, path) = queue.removeLast()
            if (pos[0] == 3 && pos[1] == 3) {
                if (path.length > res) {
                    res = path.length.toLong()
                }
                continue
            }
            
            for (dir in getOpenDoors(state[0], pos, path)) {
                val nextPath = path + dir
                val nextPos = pos + when (dir) {
                    "U" -> IntVector.UP
                    "D" -> IntVector.DOWN
                    "L" -> IntVector.LEFT
                    else -> IntVector.RIGHT
                }
                queue.addLast(Pair(nextPos, nextPath))
            }
        }
        return res
    }

    fun getOpenDoors(password: String, pos: IntVector, path: String): List<String> {
        val md5 = (password + path).md5()
        val res = mutableListOf<String>()
        if (pos[0] != 3 || pos[1] != 3) {
            if (md5[0].code > 0x61 && pos[0] > 0) res.add("U")
            if (md5[1].code > 0x61 && pos[0] < 3) res.add("D")
            if (md5[2].code > 0x61 && pos[1] > 0) res.add("L")
            if (md5[3].code > 0x61 && pos[1] < 3) res.add("R")
        }
        return res
    }
}