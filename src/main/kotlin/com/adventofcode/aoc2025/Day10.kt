package com.adventofcode.aoc2025

import org.openjdk.jmh.annotations.*
import com.adventofcode.util.File
import com.adventofcode.util.IntVector
import com.adventofcode.util.IntMatrix
import java.util.concurrent.TimeUnit
import kotlin.math.max
import kotlin.math.min
import kotlin.math.abs

class Node(val depth: Int, val count: IntVector, val state: IntVector) {}

@State(Scope.Thread)
open class State10(fileName: String = "src/main/resources/input/2025/10.txt") : File(fileName) {}

open class Day10 {
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part1(state: State10): Long {
        var res = 0L
        for (line in state) {
            val tok = line.split(" ")
            val buttons = tok.size - 2
            val outputs = tok[0].length - 2
            val goal = IntVector(outputs) { i -> if (tok[0][i+1] == '#') 1 else 0 }
            // println(tok)
            val basis = IntMatrix(outputs, buttons) { _,_ -> 0 }
            for (c in 0..<buttons) {
                val buttonTok = tok[c+1].substring(1, tok[c+1].length - 1).split(",").map(String::toInt)
                for (r in 0..<outputs) {
                    if (buttonTok.contains(r)) {
                        basis[r, c] = 1
                    }
                }
            }
            // println(basis)
            // println(findAllCombos(basis, goal))
            val one = IntVector(buttons) { _ -> 1 }
            res += findAllCombos(basis, goal).map { combo -> combo * one }.min().toLong()
        }
        return res
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part2(state: State10): Long {
        var res = 0L
        for ((idx, line) in state.withIndex()) {
        // val line = state[0]
            val tok = line.split(" ")
            val buttons = tok.size - 2
            val outputs = tok[0].length - 2
            val goal = IntVector(outputs) { _ -> 0 }
            val joltageTok = tok[tok.size-1].substring(1,tok[tok.size-1].length-1).split(",")
            for (i in 0..<outputs) {
                goal[i] = joltageTok[i].toInt()
            }
            // println(tok)
            val basis = IntMatrix(outputs, buttons) { _,_ -> 0 }
            for (c in 0..<buttons) {
                val buttonTok = tok[c+1].substring(1, tok[c+1].length - 1).split(",").map(String::toInt)
                for (r in 0..<outputs) {
                    if (buttonTok.contains(r)) {
                        basis[r, c] = 1
                    }
                }
            }
            res += solveForJoltage(basis, goal)
            println("Line ${idx+1}/187: answer ${CACHE.get(Pair(basis, goal))}")
        }
        return res
    }

    fun findAllCombos(basis: IntMatrix, goal: IntVector): List<IntVector> {
        var res = mutableListOf<IntVector>()
        val queue = ArrayDeque<Node>()
        queue.addLast(Node(0, IntVector(basis.cols ) { 0 }, IntVector(basis.rows) { 0 }))
        while (queue.isNotEmpty()) {
            val curr = queue.removeAt(0)
            if (curr.state % 2 == goal) {
                res.add(curr.count)
            } else if (curr.depth < basis.cols) {
                queue.addLast(Node(curr.depth+1, curr.count + IntVector(basis.cols) { i -> if (i == curr.depth) 1 else 0 }, curr.state + basis.col(curr.depth)))
                queue.addLast(Node(curr.depth+1, curr.count, curr.state))
            }
        }
        return res
    }

    fun solveForJoltage(basis: IntMatrix, goal: IntVector): Long {
        if (CACHE.containsKey(Pair(basis, goal))) {
            return CACHE.get(Pair(basis, goal))!!
        }

        if ((0..<goal.size).any { goal[it] < 0 }) {
            return Int.MAX_VALUE.toLong()
        } else if ((0..<goal.size).all { goal[it] == 0}) {
            return 0
        }

        val options = findAllCombos(basis, goal % 2)
        if (options.isEmpty()) {
            return Int.MAX_VALUE.toLong()
        }

        // println("Options for $goal are $options")

        val one = IntVector(basis.cols) { _ -> 1 }
        val res = options.map { combo ->
            combo * one + 2 * solveForJoltage(basis, (goal - basis * combo) / 2)
        }.min()
        CACHE.put(Pair(basis, goal), res)
        return res
    }
}

val CACHE = mutableMapOf<Pair<IntMatrix, IntVector>, Long>()