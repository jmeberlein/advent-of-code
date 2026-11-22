package com.adventofcode.aoc2016

import org.openjdk.jmh.annotations.*
import com.adventofcode.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.min
import kotlin.math.max

@State(Scope.Thread)
open class State22(fileName: String = "src/main/resources/input/aoc2016/22.txt") : File(fileName) {
    var nodes = (2..<size).map {
        val toks = this[it].split(Regex("\\s+"))
        Triple(toks[0], toks[2].dropLast(1).toInt(), toks[3].dropLast(1).toInt())
    }
}

open class Day22 {
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part1(state: State22): Long {
        var res = 0L
        for (i in 0..<state.nodes.size) {
            for (j in 0..<state.nodes.size) {
                if (i != j && state.nodes[i].second != 0 && state.nodes[i].second <= state.nodes[j].third) {
                    res++
                }
            }
        }
        return res
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part2(state: State22): Long {
        var res = 0L
        val (maxCol, maxRow) = state.nodes.last().first.split(Regex("-[xy]")).slice(1..2)
        val grid = Grid<Char>(maxRow.toInt() + 1, maxCol.toInt() + 1) { r, c -> '.' }
        var start = IntVector.of(0, 0)
        grid[0, 0] = 'G'
        grid[0, maxCol.toInt()] = '*'
        for (node in state.nodes) {
            val (c, r) = node.first.split(Regex("-[xy]")).slice(1..2)
            if (node.second == 0) {
                grid[r.toInt(), c.toInt()] = ' '
                start = IntVector.of(r.toInt(), c.toInt())
            } else if (node.second > 200) {
                grid[r.toInt(), c.toInt()] = '#'
            }
        }
        val lpaGrid = LPAGrid(maxRow.toInt() + 1, maxCol.toInt() + 1, start, IntVector.of(0,maxCol.toInt()), true) { r, c -> grid[r, c] == '#' }
        val distToData = lpaGrid.reported[lpaGrid.dest].toLong()
        val distAlongTop = 5 * (maxCol.toInt() - 1)
        return distToData + distAlongTop
    }
}