package com.adventofcode.aoc2016

import org.openjdk.jmh.annotations.*
import com.adventofcode.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.min
import kotlin.math.max
import java.security.MessageDigest

@State(Scope.Thread)
open class State14(fileName: String = "src/main/resources/input/aoc2016/14.txt") : File(fileName)

open class Day14 {
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part1(state: State14): Long {
        var res = 0L
        var salt = 0L
        var keys = 0

        val tripletRegex = Regex("""([\da-f])\1\1""")
        while (keys < 64) {
            val md5 = (state[0] + salt).md5()
            val triplet = tripletRegex.find(md5)
            if (triplet != null) {
                val quintuplet = triplet.groupValues[1].repeat(5)
                if ((1..1000).any { quintuplet in (state[0] + (salt + it)).md5() }) {
                    keys++
                }
            }

            salt++
        }

        return salt - 1
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part2(state: State14): Long {
        var res = 0L
        var salt = 0L
        var keys = 0
        val hashes = mutableMapOf<Long, String>()

        val tripletRegex = Regex("""([\da-f])\1\1""")
        while (keys < 64) {
            val md5 = hashes.getOrPut(salt) { stretch(state[0] + salt) }
            val triplet = tripletRegex.find(md5)
            if (triplet != null) {
                val quintuplet = triplet.groupValues[1].repeat(5)
                if ((1..1000).any { quintuplet in hashes.getOrPut(salt + it) { stretch(state[0] + (salt + it)) } }) {
                    keys++
                }
            }

            salt++
        }

        return salt - 1
    }

    fun stretch(str: String): String {
        // var bytes = str.toByteArray(Charsets.UTF_8)
        // for (i in 0..2016) {
        //     bytes = MessageDigest.getInstance("MD5").digest(bytes)
        // }
        // return bytes.joinToString("") { "%02x".format(it) }
        var res = str
        for (i in 0..2016) {
            res = res.md5()
        }
        return res
    }
}