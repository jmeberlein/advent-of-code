package com.adventofcode.aoc2016

import org.openjdk.jmh.annotations.*
import com.adventofcode.util.*
import com.sun.org.apache.xpath.internal.operations.Bool
import java.util.concurrent.TimeUnit
import kotlin.math.min
import kotlin.math.max

@State(Scope.Thread)
open class State16(fileName: String = "src/main/resources/input/aoc2016/16.txt") : File(fileName)

open class Day16 {
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part1(state: State16): Long {
        var res = 0L
        var length = state[0].toInt()
        var dragonLength = state[2].length
        while (dragonLength < length) {
            dragonLength = 2 * dragonLength + 1
        }

        var layers = 0
        while (length % 2 == 0) {
            layers++
            length /= 2
        }

        for (i in 0..<length) {
            if (getChecksum(state[2], i, dragonLength, layers)) {
                print('1')
            } else {
                print('0')
            }
        }
        println()

        return res
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part2(state: State16): Long {
        var res = 0L
        var length = state[1].toInt()
        var dragonLength = state[2].length
        while (dragonLength < length) {
            dragonLength = 2 * dragonLength + 1
        }

        var layers = 0
        while (length % 2 == 0) {
            layers++
            length /= 2
        }
        for (i in 0..<length) {
            if (getChecksum(state[2], i, dragonLength, layers)) {
                print('1')
            } else {
                print('0')
            }
        }
        println()
        return res
    }

    fun getChecksum(base: String, pos: Int, dragonLength: Int, layer: Int): Boolean {
        if (layer == 0) {
            return getDragon(base, pos, dragonLength)
        } else {
            return !(getChecksum(base, pos * 2, dragonLength, layer - 1) xor getChecksum(base, pos * 2 + 1, dragonLength, layer - 1))
        }
    }

    fun getDragon(base: String, pos: Int, dragonLength: Int): Boolean {
        var lt = 0
        var rt = dragonLength - 1
        var wentRight = false
        while (rt - lt >= base.length) {
            val mid = (rt + lt) / 2
            if (pos == mid) {
                return wentRight
            } else if (pos < mid) {
                wentRight = false
                rt = mid - 1
            } else if (pos > mid) {
                wentRight = true
                lt = mid + 1
            }
        }

        if (!wentRight) {
            return base[pos - lt] == '1'
        } else {
            return base[rt - pos] == '0'
        }
    }
}
