package com.adventofcode.aoc2015

import org.openjdk.jmh.annotations.*
import com.adventofcode.util.*
import java.util.concurrent.TimeUnit

@State(Scope.Thread)
open class State09(fileName: String = "src/main/resources/input/2015/09.txt") : File(fileName)

open class Day09 {
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part1(state: State09): Long {
        var res = Long.MAX_VALUE
        var cities = state.map { it.split(" ").slice(listOf(0,2)) }.flatten().distinct()
        var distances = Grid(cities.size) { _,_ -> 0 }
        state.forEach { line->
            val (from, to, dist) = line.split(" ").slice(listOf(0,2,4))
            distances[cities.indexOf(from), cities.indexOf(to)] = dist.toInt()
            distances[cities.indexOf(to), cities.indexOf(from)] = dist.toInt()
        }
        for (perm in (0..<cities.size).toList().allPermutations()) {
            val test = perm.zipWithNext().map { (from, to) -> distances[from, to] }.sum()
            if (test < res) {
                res = test.toLong()
            }
        }
        return res
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part2(state: State09): Long {
        var res = 0L
        var cities = state.map { it.split(" ").slice(listOf(0,2)) }.flatten().distinct()
        var distances = Grid(cities.size) { _,_ -> 0 }
        state.forEach { line->
            val (from, to, dist) = line.split(" ").slice(listOf(0,2,4))
            distances[cities.indexOf(from), cities.indexOf(to)] = dist.toInt()
            distances[cities.indexOf(to), cities.indexOf(from)] = dist.toInt()
        }
        for (perm in (0..<cities.size).toList().allPermutations()) {
            val test = perm.zipWithNext().map { (from, to) -> distances[from, to] }.sum()
            if (test > res) {
                res = test.toLong()
            }
        }
        return res
    }

}
