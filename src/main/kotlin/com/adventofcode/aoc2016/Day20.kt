package com.adventofcode.aoc2016

import org.openjdk.jmh.annotations.*
import com.adventofcode.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.min
import kotlin.math.max

@State(Scope.Thread)
open class State20(fileName: String = "src/main/resources/input/aoc2016/20.txt") : File(fileName)

open class Day20 {
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part1(state: State20): Long {
        var res = 0L
        val ranges = state.map {
            Pair(it.split('-')[0].toLong(), it.split('-')[1].toLong())
        }.sortedWith(
            compareBy<Pair<Long, Long>> {
                it.first
            }.thenBy { 
                it.second
            }
        )
        for ((lt, rt) in ranges) {
            if (lt <= res && rt >= res) {
                res = rt + 1
            }
        }
        return res
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part2(state: State20): Long {
        var res = UInt.MAX_VALUE.toLong() + 1
        var ranges = state.map {
            var range = it.split('-')
            range[0].toLong()..range[1].toLong()
        }.toMutableList()
        ranges = mergeRanges(ranges)
        for (range in ranges) {
            res -= range.endInclusive - range.start + 1
        }
        return res
    }

    fun mergeRanges(ranges: MutableList<LongRange>): MutableList<LongRange> {
        ranges.sortBy { it.start }
        var i = 0
        while (i < ranges.size) {
            if (i < ranges.size - 1 && ranges[i+1].contains(ranges[i].endInclusive)) {
                ranges.set(i, ranges[i].start..ranges[i+1].endInclusive)
                ranges.remove(ranges[i+1])
            } else if (i < ranges.size - 1 && ranges[i].contains(ranges[i+1].endInclusive)) {
                ranges.remove(ranges[i+1])
            } else {
                i++
            }
        }
        return ranges
    }
}