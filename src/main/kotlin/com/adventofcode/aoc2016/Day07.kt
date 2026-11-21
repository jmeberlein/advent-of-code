package com.adventofcode.aoc2016

import org.openjdk.jmh.annotations.*
import com.adventofcode.util.*
import java.util.concurrent.TimeUnit

@State(Scope.Thread)
open class State07(fileName: String = "src/main/resources/input/aoc2016/07.txt") : File(fileName)

open class Day07 {
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part1(state: State07): Long {
        var res = 0L
        for (line in state) {
            val toks = line.split(Regex("[\\[\\]]"))
            var hasAbba = false
            outer@ for (i in 0..<toks.size step 2) {
                val tok = toks[i]
                for (j in 0..<(tok.length - 3)) {
                    if (tok[j] == tok[j+3] && tok[j+1] == tok[j+2] && tok[j] != tok[j+1]) {
                        hasAbba = true
                        break@outer
                    }
                }
            }

            var hasHyperAbba = false
            outer@ for (i in 1..<toks.size step 2) {
                val tok = toks[i]
                for (j in 0..<(tok.length - 3)) {
                    if (tok[j] == tok[j+3] && tok[j+1] == tok[j+2] && tok[j] != tok[j+1]) {
                        hasHyperAbba = true
                        break@outer
                    }
                }
            }

            if (hasAbba && !hasHyperAbba) {
                res++
            }
        }
        return res
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part2(state: State07): Long {
        var res = 0L
        line@ for (line in state) {
            val toks = line.split(Regex("[\\[\\]]"))
            val plain = toks.slice(0..<toks.size step 2)
            val hyper = toks.slice(1..<toks.size step 2)
            
            tok@ for (tok in plain) {
                for (i in 0..<(tok.length - 2)) {
                    if (tok[i] == tok[i+2] && tok[i] != tok[i+1]) {
                        val a = tok[i]
                        val b = tok[i+1]
                        val bab = "$b$a$b"
                        if (hyper.any { bab in it }) {
                            res++
                            continue@line
                        }
                    }
                }
            }
        }
        return res
    }

}