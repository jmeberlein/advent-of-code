package com.adventofcode.aoc2016

import org.openjdk.jmh.annotations.*
import com.adventofcode.util.*
import java.util.concurrent.TimeUnit

@State(Scope.Thread)
open class State08(fileName: String = "src/main/resources/input/aoc2016/08.txt") : File(fileName)

open class Day08 {
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part1(state: State08): Long {
        var res = 0L
        var grid = Grid<Boolean>(6, 50) { r, c -> false }
        // var grid = Grid<Boolean>(3, 7) { r, c -> false }
        for (line in state) {
            val toks = line.split(" ")
            if (toks[0] == "rect") {
                val width = toks[1].split("x")[0].toInt()
                val height = toks[1].split("x")[1].toInt()
                rect(grid, width, height)
            } else {
                val idx = toks[2].substring(2).toInt()
                val dist = toks[4].toInt()
                if (toks[1] == "column") {
                    rotateColumn(grid, idx, dist)
                } else {
                    rotateRow(grid, idx, dist)
                }
            }

            for (r in 0..<grid.rows) {
                for (c in 0..<grid.cols) {
                    if (grid[r, c]) {
                        print("#")
                    } else {
                        print(".")
                    }
                }
                println()
            }
            println()
        }

        for (r in 0..<grid.rows) {
            for (c in 0..<grid.cols) {
                if (grid[r, c]) {
                    res++
                    print("#")
                } else {
                    print(".")
                }
            }
            println()
        }

        return res
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part2(state: State08): Long {
        var res = 0L
        return res
    }

    fun rect(grid: Grid<Boolean>, width: Int, height: Int) {
        for (r in 0..<height) {
            for (c in 0..<width) {
                grid[r, c] = true
            }
        }
    }

    fun rotateRow(grid: Grid<Boolean>, row: Int, dist: Int) {
        for (i in 0..<dist) {
            val tmp = grid[row, grid.cols-1]
            for (j in (grid.cols - 1) downTo 1) {
                grid[row, j] = grid[row, j-1]
            }
            grid[row, 0] = tmp
        }
    }

    fun rotateColumn(grid: Grid<Boolean>, col: Int, dist: Int) {
        for (i in 0..<dist) {
            val tmp = grid[grid.rows-1, col]
            for (j in (grid.rows - 1) downTo 1) {
                grid[j, col] = grid[j-1, col]
            }
            grid[0, col] = tmp
        }
    }
}