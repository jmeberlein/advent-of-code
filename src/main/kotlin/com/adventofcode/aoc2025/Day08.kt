package com.adventofcode.aoc2025

import org.openjdk.jmh.annotations.*
import com.adventofcode.util.File
import com.adventofcode.util.Grid
import com.adventofcode.util.Vector
import java.util.concurrent.TimeUnit
import kotlin.math.min

@State(Scope.Thread)
open class State08(fileName: String = "src/main/resources/input/2025/08.txt") : File(fileName) {
    val points = List<Vector>(this.size) { i ->
        val tok = this[i].split(",")
        Vector.of(tok[0].toDouble(), tok[1].toDouble(), tok[2].toDouble())
    }
}

open class Day08 {
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part1(state: State08): Long {
        var res = 1L

        var edges = firstNEdges(state.points, 1000)

        var groups = mutableSetOf<Set<Vector>>()
        for (point in state.points) {
            groups.add(mutableSetOf(point))
        }
        for (edge in edges) {
            var group1 = groups.find { it.contains(edge.first) }!!
            var group2 = groups.find { it.contains(edge.second) }!!

            groups.remove(group1)
            groups.remove(group2)
            groups.add(group1 union group2)
        }

        var groupSizes = groups.map { it.size }.sortedDescending()
        res *= groupSizes[0]
        res *= groupSizes[1]
        res *= groupSizes[2]

        return res
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part2(state: State08): Long {
        var res = 0L
        var numEdges = (state.points.size - 1) * (state.points.size - 2) / 2 + 1

        var edges = firstNEdges(state.points, numEdges)

        var groups = mutableSetOf<Set<Vector>>()
        for (point in state.points) {
            groups.add(mutableSetOf(point))
        }
        for (edge in edges) {
            var group1 = groups.find { it.contains(edge.first) }!!
            var group2 = groups.find { it.contains(edge.second) }!!

            groups.remove(group1)
            groups.remove(group2)
            groups.add(group1 union group2)

            if (groups.size == 1) {
                return edge.first[0].toLong() * edge.second[0].toLong()
            }
        }

        return res
    }

    fun firstNEdges(points: List<Vector>, numEdges: Int): MutableList<Pair<Vector, Vector>> {
        var edges = mutableListOf<Pair<Vector, Vector>>()
        for (i in 0..<points.size) {
            var v1 = points[i]
            for (j in (i+1)..<points.size) {
                var v2 = points[j]

                var lt = 0
                var rt = edges.size
                while (lt < rt) {
                    var md = lt + (rt - lt) / 2
                    if ((v1 - v2).magnitude() < (edges[md].first - edges[md].second).magnitude()) {
                        rt = md
                    } else {
                        lt = md + 1
                    }
                }
                if (lt < numEdges) {
                    edges.add(lt, Pair(v1, v2))
                }
                if (edges.size > numEdges) {
                    edges.removeAt(numEdges)
                }
            }
        }
        return edges
    }
}
