package com.adventofcode.aoc2025

import org.openjdk.jmh.annotations.*
import com.adventofcode.util.File
import com.adventofcode.util.Vector
import java.util.concurrent.TimeUnit
import kotlin.math.max
import kotlin.math.min
import kotlin.math.abs

class Node01(val depth: Int, val count: Int, val state: Vector) {}
class Node02(val depth: Int, val count: Int, val lights: Vector, val joltage: Vector) {}

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
            val queue = ArrayDeque<Node01>()
            queue.addLast(Node01(0, 0, Vector(goal.size) { 0.0 }))
            while (queue.isNotEmpty()) {
                val curr = queue.removeAt(0)
                if (curr.state == goal) {
                    if (curr.count < buttons) {
                        buttons = curr.count.toLong()
                    }
                } else if (curr.depth < basis.size) {
                    queue.addLast(Node01(curr.depth+1, curr.count+1, (curr.state + basis[curr.depth]) % 2))
                    queue.addLast(Node01(curr.depth+1, curr.count, curr.state))
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
        // for (line in state) {
        val line = state[0]
            var buttons = Long.MAX_VALUE
            val tok = line.split(" ")
            val targetLights = Vector(tok[0].length - 2) { i -> if (tok[0][i+1] == '#') 1.0 else 0.0 }
            val joltages = tok[tok.size-1].substring(1, tok[tok.size-1].length - 1).split(",")
            val targetJoltage = Vector(targetLights.size) { i -> joltages[i].toDouble() }
            val basisLights = List(tok.size - 2) { i ->
                val lights = tok[i+1].substring(1, tok[i+1].length - 1).split(",").map(String::toInt)
                Vector(targetLights.size) { j -> if (lights.contains(j)) 1.0 else 0.0 }
            }
            val basisJoltages = List(tok.size - 2) { i ->
                val lights = tok[i+1].substring(1, tok[i+1].length - 1).split(",").map(String::toInt)
                Vector(targetLights.size) { j -> if (lights.contains((j + targetLights.size - 1) % targetLights.size)) 1.0 else 0.0 }
            }
            val queue = ArrayDeque<Node02>()
            queue.addLast(Node02(0, 0, Vector(targetLights.size) { 0.0 }, Vector(targetLights.size) { 0.0 }))
            while (queue.isNotEmpty()) {
                val curr = queue.removeAt(0)
                println("Inspecting depth=${curr.depth}, count=${curr.count}, lights=${curr.lights}, joltage=${curr.joltage}")
                if (curr.lights == targetLights && curr.joltage == targetJoltage) {
                    if (curr.count < buttons) {
                        buttons = curr.count.toLong()
                    }
                } else if (curr.depth < basisLights.size) {
                    var lights = curr.lights
                    var count = curr.count
                    var joltage = curr.joltage
                    while ((0..<targetLights.size).all { i -> joltage[i] <= targetJoltage[i]}) {
                        queue.addLast(Node02(curr.depth+1, count, lights % 2, joltage))
                        count++
                        lights += basisLights[curr.depth]
                        joltage += basisJoltages[curr.depth]
                    }
                }
            }
            res += buttons
        // }
        return res
    }
}