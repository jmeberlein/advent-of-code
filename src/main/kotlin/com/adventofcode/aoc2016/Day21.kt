package com.adventofcode.aoc2016

import org.openjdk.jmh.annotations.*
import com.adventofcode.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.min
import kotlin.math.max

@State(Scope.Thread)
open class State21(fileName: String = "src/main/resources/input/aoc2016/21.txt") : File(fileName)

open class Day21 {
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part1(state: State21): Long {
        var res = 0L
        var str = state[0]
        for (i in 1..<state.size) {
            val toks = state[i].split(" ")
            if (toks[0] == "swap" && toks[1] == "position") {
                str = swap(str, toks[2].toInt(), toks[5].toInt())
            } else if (toks[0] == "swap") {
                str = swap(str, toks[2], toks[5])
            } else if (toks[0] == "rotate" && toks[1] == "left") {
                str = rotLeft(str, toks[2].toInt())
            } else if (toks[0] == "rotate" && toks[1] == "right") {
                str = rotRight(str, toks[2].toInt())
            } else if (toks[0] == "rotate") {
                str = rotBy(str, toks[6])
            } else if (toks[0] == "reverse") {
                str = reverse(str, toks[2].toInt(), toks[4].toInt())
            } else {
                str = move(str, toks[2].toInt(), toks[5].toInt())
            }
        }
        println(str)

        return res
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part2(state: State21): Long {
        var res = 0L
        var str = "fbgdceah"
        for (i in (state.size - 1) downTo 1) {
            val toks = state[i].split(" ")
            if (toks[0] == "swap" && toks[1] == "position") {
                str = swap(str, toks[2].toInt(), toks[5].toInt())
            } else if (toks[0] == "swap") {
                str = swap(str, toks[2], toks[5])
            } else if (toks[0] == "rotate" && toks[1] == "left") {
                str = rotRight(str, toks[2].toInt())
            } else if (toks[0] == "rotate" && toks[1] == "right") {
                str = rotLeft(str, toks[2].toInt())
            } else if (toks[0] == "rotate") {
                str = unRotBy(str, toks[6])
            } else if (toks[0] == "reverse") {
                str = reverse(str, toks[2].toInt(), toks[4].toInt())
            } else {
                str = move(str, toks[5].toInt(), toks[2].toInt())
            }
        }
        println(str)
        return res
    }

    fun swap(str: String, x: Int, y: Int): String {
        val lt = min(x, y)
        val rt = max(x, y)
        return str.substring(0, lt) + str[rt] + str.substring(lt+1, rt) + str[lt] + str.substring(rt+1)
    }

    fun swap(str: String, x: String, y: String): String {
        return swap(str, str.indexOf(x), str.indexOf(y))
    }

    fun rotRight(str: String, dist: Int): String {
        return (str.substring(0, str.length - dist).reversed() + str.substring(str.length - dist).reversed()).reversed()
    }

    fun rotLeft(str: String, dist: Int): String {
        return (str.substring(0, dist).reversed() + str.substring(dist).reversed()).reversed()
    }

    fun reverse(str: String, x: Int, y: Int): String {
        return str.substring(0, x) + str.substring(x, y+1).reversed() + str.substring(y+1)
    }

    fun move(str: String, x: Int, y: Int): String {
        if (x < y) {
            return str.substring(0, x) + str.substring(x+1, y+1) + str[x] + str.substring(y+1) 
        } else {
            return str.substring(0, y) + str[x] + str.substring(y, x) + str.substring(x+1)
        }
    }

    fun rotBy(str: String, x: String): String {
        var dist = str.indexOf(x)
        dist += if (dist >= 4) 2 else 1
        dist %= str.length
        return rotRight(str, dist)
    }

    // This actually isn't deterministic. For example, if there are 7 characters,
    // the character being at index 3 and the character being at index 6 will both
    // rotate it such that it winds up at index 0. However, at least for 8 characters,
    // like in the input, the +1 if idx >= 4 "just so happens" to make it deterministic.
    // This is me calculating the offsets by hand and hard-coding things.
    fun unRotBy(str: String, x: String): String {
        return when(str.indexOf(x)) {
            0 -> rotLeft(str, 1)
            1 -> rotLeft(str, 1)
            2 -> rotRight(str, 2)
            3 -> rotLeft(str, 2)
            4 -> rotRight(str, 1)
            5 -> rotLeft(str, 3)
            6 -> str
            7 -> rotLeft(str, 4)
            else -> str
        }
    }
}