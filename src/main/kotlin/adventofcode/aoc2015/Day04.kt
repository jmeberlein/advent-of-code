package com.adventofcode.aoc2015

import org.openjdk.jmh.annotations.*
import com.adventofcode.util.File
import java.security.MessageDigest
import java.math.BigInteger
import java.util.concurrent.TimeUnit

@State(Scope.Thread)
open class State04(fileName: String = "src/main/resources/input/2015/04.txt") : File(fileName)

open class Day04 {
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part1(state: State04): Long {
        var res = 1L
        while (true) {
            val md5 = md5((state[0] + res.toString()))
            if (md5.startsWith("00000"))  {
                return res
            }
            res++
        }
        // return res
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part2(state: State04): Long {
        var res = 1L
        while (true) {
            val md5 = md5((state[0] + res.toString()))
            if (md5.startsWith("000000")) {
                return res
            }
            res++
        }
        // return res
    }

    fun md5(s: String): String {
        val md = MessageDigest.getInstance("MD5")
        val digest = md.digest(s.toByteArray(Charsets.UTF_8))
        return BigInteger(1, digest).toString(16).padStart(32, '0')
    }
}
