package com.adventofcode.aoc2015

import org.openjdk.jmh.annotations.*
import com.adventofcode.util.*
import java.util.concurrent.TimeUnit

@State(Scope.Thread)
open class State14(fileName: String = "src/main/resources/input/aoc2015/14.txt") : File(fileName)

class Reindeer(
    val speed: Int,
    val flightTime: Int,
    val restTime: Int,
    var isFlying: Boolean = true,
    var score: Long = 0,
    var dist: Int = 0,
    var timer: Int = flightTime
) {
    fun step() {
        if (isFlying) {
            dist += speed
        }
        if (--timer == 0) {
            isFlying = !isFlying
            timer = if (isFlying) flightTime else restTime 
        }
    }
}

open class Day14 {
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part1(state: State14): Long {
        var res = 0L
        state.forEach { line->
            var (speed, flightTime, restTime) = line.split(" ").slice(listOf(3, 6, 13)).map { it.toLong() }
            var cycleTime = flightTime + restTime
            var dist = speed * flightTime * (2503 / cycleTime) + speed * minOf(flightTime, 2503 % cycleTime)
            if (dist > res) {
                res = dist
            }
        }
        return res
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part2(state: State14): Long {
        var res = 0L
        var reindeer = state.map { line->
            var (speed, flightTime, restTime) = line.split(" ").slice(listOf(3, 6, 13))
            Reindeer(speed.toInt(), flightTime.toInt(), restTime.toInt())
        }
        for (i in 0..<2503) {
            reindeer.forEach { it.step() }
            val maxDist = reindeer.maxBy { it.dist }.dist
            reindeer.forEach {
                if (it.dist == maxDist) {
                    it.score++
                }
            }
        }
        return reindeer.maxBy { it.score }.score
    }

}
