package com.adventofcode.aoc2016

import org.openjdk.jmh.annotations.*
import com.adventofcode.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.min
import kotlin.math.max

@State(Scope.Thread)
open class State10(fileName: String = "src/main/resources/input/aoc2016/10.txt") : File(fileName)

open class Day10 {
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part1(state: State10): Long {
        var res = 0L

        // Find number of bots + outputs
        var maxBot = 0
        var maxOut = 0
        for (line in state) {
            val toks = line.split(" ")
            for (i in 0..<toks.size) {
                if (toks[i] == "bot" && toks[i+1].toInt() > maxBot) {
                    maxBot = toks[i+1].toInt()
                } else if (toks[i] == "output" && toks[i+1].toInt() > maxOut) {
                    maxOut = toks[i+1].toInt()
                }
            }
        }

        // Parse data
        val bots = IntMatrix(maxBot + 1, 2) { r, c -> 0 }
        var outputs = IntArray(maxOut + 1)
        var targets = Array<Pair<Int, Int>>(maxBot + 1) { Pair(0, 0) }
        for (line in state) {
            val toks = line.split(" ")
            if (toks[0] == "value") {
                val bot = toks[5].toInt()
                val value = toks[1].toInt()
                bots[bot, 1] = bots[bot, 0]
                bots[bot, 0] = value
            } else {
                val bot = toks[1].toInt()
                val low = if (toks[5] == "output") toks[6].toInt().inv() else toks[6].toInt()
                val high = if (toks[10] == "output") toks[11].toInt().inv() else toks[11].toInt()
                targets[bot] = Pair(low, high)
            }
        }

        // Process nodes
        val toProcess = ArrayDeque<Int>()
        for (i in 0..maxBot) {
            if (bots[i, 1] != 0) {
                toProcess.addLast(i)
            }
        }
        while (toProcess.size > 0) {
            val curr = toProcess.removeFirst()
            val low = min(bots[curr, 0], bots[curr, 1])
            val high = max(bots[curr, 0], bots[curr, 1])

            // if (low == 2 && high == 5) {
            if (low == 17 && high == 61) {
                println(curr.toLong())
            }

            if (targets[curr].first < 0) {
                outputs[targets[curr].first.inv()] = low
            } else {
                bots[targets[curr].first, 1] = bots[targets[curr].first, 0]
                bots[targets[curr].first, 0] = low
                if (bots[targets[curr].first, 1] != 0) {
                    toProcess.addLast(targets[curr].first)
                }
            }
            if (targets[curr].second < 0) {
                outputs[targets[curr].second.inv()] = high
            } else {
                bots[targets[curr].second, 1] = bots[targets[curr].second, 0]
                bots[targets[curr].second, 0] = high
                if (bots[targets[curr].second, 1] != 0) {
                    toProcess.addLast(targets[curr].second)
                }
            }
        }

        return (outputs[0] * outputs[1] * outputs[2]).toLong()
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part2(state: State10): Long {
        var res = 0L
        
        return res
    }
}