package com.adventofcode.aoc2016

import org.openjdk.jmh.annotations.*
import com.adventofcode.util.*
import java.util.concurrent.TimeUnit

@State(Scope.Thread)
open class State04(fileName: String = "src/main/resources/input/aoc2016/04.txt") : File(fileName)

open class Day04 {
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part1(state: State04): Long {
        var res = 0L
        for (line in state) {
            val name = line.dropLast(10)
            val tail = line.takeLast(10)
            val id = tail.substring(0, 3).toLong()
            val checksum = tail.substring(4, 9)

            var counts = Array(26) { Pair((it + 0x61).toChar(), 0) }
            for (c in name) {
                if (c != '-') {
                    counts[c.code - 0x61] = Pair(counts[c.code - 0x61].first, counts[c.code - 0x61].second - 1)
                }
            }
            counts.sortBy { it.second }

            if ((0..<5).all { checksum[it] == counts[it].first }) {
                res += id
            }
        }
        return res
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part2(state: State04): Long {
        var res = 0L
        for (line in state) {
            val name = line.dropLast(10)
            val tail = line.takeLast(10)
            val id = tail.substring(0, 3).toLong()
            val checksum = tail.substring(4, 9)

            var counts = Array(26) { Pair((it + 0x61).toChar(), 0) }
            for (c in name) {
                if (c != '-') {
                    counts[c.code - 0x61] = Pair(counts[c.code - 0x61].first, counts[c.code - 0x61].second - 1)
                }
            }
            counts.sortBy { it.second }

            if ((0..<5).all { checksum[it] == counts[it].first }) {
                val decrypted = name.map {
                    if (it == '-') {
                        '-'
                    } else {
                        (0x61 + (it.code + id - 0x61) % 26).toChar()
                    }
                }.joinToString("")
                if ("north" in decrypted) {
                    println("$decrypted: $id")
                }
            }
        }
        return res
    }

}