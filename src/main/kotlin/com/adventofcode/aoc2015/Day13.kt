package com.adventofcode.aoc2015

import org.openjdk.jmh.annotations.*
import com.adventofcode.util.*
import java.util.concurrent.TimeUnit

@State(Scope.Thread)
open class State13(fileName: String = "src/main/resources/input/2015/13.txt") : File(fileName)

open class Day13 {
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part1(state: State13): Long {
        var res = 0L
        var guests = state.map { it.split(" ")[0] }.distinct()
        var happinesses = Grid(guests.size) { _,_ -> 0 }
        state.forEach { line->
            var (guestA, positive, value, guestB) = line.split(" ").slice(listOf(0,2,3,10))
            guestB = guestB.substring(0,guestB.lastIndex)
            happinesses[guests.indexOf(guestA), guests.indexOf(guestB)] = value.toInt()
            if (positive == "lose") {
                happinesses[guests.indexOf(guestA), guests.indexOf(guestB)] *= -1
            }
        }
        for (perm in (0..<guests.size).toList().allPermutations()) {
            var test = perm.zipWithNext().map { (from, to) -> happinesses[from, to] + happinesses[to, from] }.sum()
            test += happinesses[perm[perm.lastIndex], perm[0]]
            test += happinesses[perm[0], perm[perm.lastIndex]]
            if (test > res) {
                res = test.toLong()
            }
        }
        return res
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part2(state: State13): Long {
        var res = 0L
        var guests = state.map { it.split(" ")[0] }.distinct()
        var happinesses = Grid(guests.size + 1) { _,_ -> 0 }
        state.forEach { line->
            var (guestA, positive, value, guestB) = line.split(" ").slice(listOf(0,2,3,10))
            guestB = guestB.substring(0,guestB.lastIndex)
            happinesses[guests.indexOf(guestA), guests.indexOf(guestB)] = value.toInt()
            if (positive == "lose") {
                happinesses[guests.indexOf(guestA), guests.indexOf(guestB)] *= -1
            }
        }
        for (perm in (0..guests.size).toList().allPermutations()) {
            var test = perm.zipWithNext().map { (from, to) -> happinesses[from, to] + happinesses[to, from] }.sum()
            test += happinesses[perm[perm.lastIndex], perm[0]]
            test += happinesses[perm[0], perm[perm.lastIndex]]
            if (test > res) {
                res = test.toLong()
            }
        }
        return res
    }

}
