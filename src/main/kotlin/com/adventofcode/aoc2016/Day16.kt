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
        var dragon = state[1].toCharArray().map { it == '1' }.toMutableList()
        var length = state[0].toInt()
        while (dragon.size < length) {
            val oldLength = dragon.size
            dragon.add(false)
            ((oldLength - 1) downTo 0).forEach { dragon.add(!dragon[it]) }
        }

        var checksum = dragon
        while (length % 2 == 0) {
            val tmp = mutableListOf<Boolean>()
            for (i in 0..<length step 2) {
                tmp.add(!(checksum[i] xor checksum[i+1]))
            }
            checksum = tmp
            length /= 2
        }

        println(checksum.fold("") { a, b -> a + if (b) '1' else '0'})
        return res
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part2(state: State16): Long {
        var res = 0L

        return res
    }
}
