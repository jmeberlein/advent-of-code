package com.adventofcode.aoc2025

import org.openjdk.jmh.annotations.*
import java.util.concurrent.TimeUnit
import org.apache.commons.math3.complex.Complex
import com.adventofcode.util.*
import kotlin.ranges.ClosedRange

@State(Scope.Thread)
open class State05(fileName: String = "src/main/resources/input/2025/05.txt") : File(fileName) {}

open class Day05 {
    fun part1(state: File): Long {
        return part1(state as State05)
    }

    fun part2(state: File): Long {
        return part2(state as State05)
    }

    @Benchmark
    @BenchmarkMode(Mode.SampleTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part1(state: State05): Long {
        var res = 0L
        var ranges = mutableListOf<ClosedRange<Long>>()
        var i = 0
        while (!state[i].isEmpty()) {
            var range = state[i].split("-")
            ranges.add(range[0].toLong()..range[1].toLong())
            i++
        }
        i++
        // ranges = mergeRanges(ranges)
        while (i < state.size) {
            for (range in ranges) {
                if (range.contains(state[i].toLong())) {
                    // println("${state[i].toLong()} is in the range ${range.start}-${range.endInclusive}")
                    res++
                    break
                }
            }
            i++
        }
        return res
    }

    @Benchmark
    @BenchmarkMode(Mode.SampleTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part2(state: State05): Long {
        var res = 0L
        var ranges = mutableListOf<ClosedRange<Long>>()
        var i = 0
        while (!state[i].isEmpty()) {
            var range = state[i].split("-")
            ranges.add(range[0].toLong()..range[1].toLong())
            i++
        }
        ranges = mergeRanges(ranges)
        for (range in ranges) {
            res += range.endInclusive - range.start + 1
        }
        return res
    }

    fun mergeRanges(ranges: MutableList<ClosedRange<Long>>): MutableList<ClosedRange<Long>> {
        ranges.sortWith(compareBy<ClosedRange<Long>> { it.start })
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
