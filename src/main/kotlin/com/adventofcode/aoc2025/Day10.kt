package com.adventofcode.aoc2025

import org.openjdk.jmh.annotations.*
import com.adventofcode.util.File
import com.adventofcode.util.Vector
import java.util.concurrent.TimeUnit
import kotlin.math.max
import kotlin.math.min
import kotlin.math.abs

class Node(val depth: Int, val count: Int, val state: Vector) {}

@State(Scope.Thread)
open class State10(fileName: String = "src/main/resources/input/2025/10.txt") : File(fileName) {}

open class Day10 {
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part1(state: State10): Long {
        var res = 0L
        for (line in state) {
            var buttons = Long.MAX_VALUE
            val tok = line.split(" ")
            val goal = Vector(tok[0].length - 2) { i -> if (tok[0][i+1] == '#') 1.0 else 0.0 }
            val basis = List(tok.size - 2) { i ->
                val lights = tok[i+1].substring(1, tok[i+1].length - 1).split(",").map(String::toInt)
                Vector(goal.size) { j -> if (lights.contains(j)) 1.0 else 0.0 }
            }
            val queue = ArrayDeque<Node>()
            queue.addLast(Node(0, 0, Vector(goal.size) { 0.0 }))
            while (queue.isNotEmpty()) {
                val curr = queue.removeAt(0)
                if (curr.state == goal) {
                    if (curr.count < buttons) {
                        buttons = curr.count.toLong()
                    }
                } else if (curr.depth < basis.size) {
                    queue.addLast(Node(curr.depth+1, curr.count+1, (curr.state + basis[curr.depth]) % 2))
                    queue.addLast(Node(curr.depth+1, curr.count, curr.state))
                }
            }
            res += buttons
        }
        return res
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part2(state: State10): Long {
        var res = 0L
        for (line in state) {
        // val line = state[0]
            var buttons = Long.MAX_VALUE
            val tok = line.split(" ")
            val numLights = tok[0].length - 2
            val joltages = tok[tok.size-1].substring(1, tok[tok.size-1].length - 1).split(",")
            val goal =  Vector(numLights) { i -> joltages[i].toDouble() }
            val basis = List(tok.size - 2) { i ->
                val lights = tok[i+1].substring(1, tok[i+1].length - 1).split(",").map(String::toInt)
                Vector(goal.size) { j -> if (lights.contains(j)) 1.0 else 0.0 }
            }
            val queue = ArrayDeque<Node>()
            queue.addLast(Node(0, 0, Vector(numLights) { 0.0 }))
            while (queue.isNotEmpty()) {
                val curr = queue.removeAt(0)
                if (curr.state == goal) {
                    if (curr.count < buttons) {
                        buttons = curr.count.toLong()
                    }
                } else if (curr.depth < basis.size) {
                    var count = curr.count
                    var joltage = curr.state
                    while ((0..<numLights).all { i -> joltage[i] <= goal[i]}) {
                        queue.addLast(Node(curr.depth+1, count, joltage))
                        count++
                        joltage += basis[curr.depth]
                    }
                }
            }
            res += buttons
        }
        return res
    }
}