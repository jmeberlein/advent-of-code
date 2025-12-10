package com.adventofcode.aoc2025

import org.openjdk.jmh.annotations.*
import com.adventofcode.util.File
import com.adventofcode.util.Vector
import java.util.concurrent.TimeUnit
import kotlin.math.max
import kotlin.math.min
import kotlin.math.abs

@State(Scope.Thread)
open class State09(fileName: String = "src/main/resources/input/2025/09.txt") : File(fileName) {
    val points = List<Vector>(this.size) { i ->
        val tok = this[i].split(",")
        Vector.of(tok[0].toDouble(), tok[1].toDouble())
    }
}

open class Day09 {
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part1(state: State09): Long {
        var res = 0L
        for (i in 0..<state.points.size) {
            for (j in (i+1)..<state.points.size) {
                val vec = state.points[i] - state.points[j] + 1
                // println("${state.points[i]} and ${state.points[j]} have area ${vec[0] * vec[1]}")
                if (abs(vec[0] * vec[1]) > res) {
                    res = abs(vec[0] * vec[1]).toLong()
                }
            }
        }
        return res
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part2(state: State09): Long {
        var res = 0L
        val edges: List<Pair<Vector, Vector>> = List(state.points.size) { i -> Pair(state.points[i], state.points[(i+1)%state.points.size]) }
        // println(isInterior(Vector.of(9.0, 4.0), edges))
        var max = Pair(Vector.of(0.6, -0.8), Vector.of(0.8, 0.6))
        for (i in 0..<state.points.size) {
            for (j in (i+1)..<state.points.size) {
                val vec = state.points[i] - state.points[j] + 1
                val p1 = Vector.of(state.points[i][0], state.points[j][1])
                val p2 = Vector.of(state.points[j][0], state.points[i][1])
                val maxR = max(p1[0], p2[0]) - 0.5
                val minR = min(p1[0], p2[0]) + 0.5
                val maxC = max(p1[1], p2[1]) - 0.5
                val minC = min(p1[1], p2[1]) + 0.5
                val c1 = Vector.of(maxR, maxC)
                val c2 = Vector.of(maxR, minC)
                val c3 = Vector.of(minR, minC)
                val c4 = Vector.of(minR, maxC)
                // println("${state.points[i]} and ${state.points[j]} have area ${vec[0] * vec[1]}")
                if (isInterior(p1, edges) && isInterior(p2, edges) && isInterior(c1, c2, edges) &&
                        isInterior(c2, c3, edges) && isInterior(c3, c4, edges) &&
                        isInterior(c4, c1, edges) && abs(vec[0] * vec[1]) > res) {
                    res = abs(vec[0] * vec[1]).toLong()
                    max = Pair(state.points[i], state.points[j])
                }
            }
        }
        println(max)
        return res
    }

    fun isInterior(v1: Vector, v2: Vector, edges: List<Pair<Vector, Vector>>): Boolean {
        for (edge in edges) {
            if (v1[0] == v2[0]) {
                if (((edge.first[0] < v1[0] && v1[0] < edge.second[0]) || (edge.first[0] > v1[0] && v1[0] > edge.second[0])) &&
                        ((v1[1] < edge.first[1] && edge.first[1] < v2[1]) || (v1[1] > edge.first[1] && edge.first[1] > v2[1]))) {
                    return false
                }
            } else {
                if (((edge.first[1] < v1[1] && v1[1] < edge.second[1]) || (edge.first[1] > v1[1] && v1[1] > edge.second[1])) &&
                        ((v1[0] < edge.first[0] && edge.first[0] < v2[0]) || (v1[0] > edge.first[0] && edge.first[0] > v2[0]))) {
                    return false
                }
            }
        }
        return true
    }

    fun isInterior(v: Vector, edges: List<Pair<Vector, Vector>>): Boolean {
        var count = 0.0
        for (edge in edges) {
            if (edge.first[0] == v[0] && v[0] == edge.second[0] && ((edge.first[1] <= v[1] && v[1] <= edge.second[1]) || (edge.first[1] >= v[1] && v[1] >= edge.second[1]))) {
                // println("$v is on $edge")
                return true
            }

            if (edge.first[1] == v[1] && v[1] == edge.second[1] && ((edge.first[0] <= v[0] && v[0] <= edge.second[0]) || (edge.first[0] >= v[0] && v[0] >= edge.second[0]))) {
                // println("$v is on $edge")
                return true
            }

            if (edge.first[0] == edge.second[0] && v[0] > edge.second[0] && ((edge.first[1] < v[1] && v[1] < edge.second[1]) || (edge.first[1] > v[1] && v[1] > edge.second[1]))) {
                // println("$v interects $edge")
                count++
            }

            if (edge.first[0] == edge.second[0] && v[0] > edge.second[0] && (edge.first[1] == v[1] || v[1] == edge.second[1])) {
                // println("$v touches $edge")
                count += 0.5
            }
        }

        return count % 2 != 0.0
    }
}
