package com.adventofcode.aoc2016

import org.openjdk.jmh.annotations.*
import com.adventofcode.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.min
import kotlin.math.max

@State(Scope.Thread)
open class State11(fileName: String = "src/main/resources/input/aoc2016/11.txt") : File(fileName)

open class Day11 {
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part1(state: State11): Long {
        var res = 0L

        // Count microchips
        var elements = mutableListOf("")
        for (line in state) {
            val toks = line.substring(0, line.length - 1).split(" ")
            for (i in 1..<toks.size) {
                if (toks[i].startsWith("generator")) {
                    elements.add(toks[i-1])
                }
            }
        }

        // Initialize grid
        val initialState = IntMatrix(elements.size, 2) { r, c -> 0 }
        initialState[0, 0] = 0 // elevator
        initialState[0, 1] = state.size - 1 // I have a spare value, so use it for floors
        for (i in 0..<state.size) {
            val toks = state[i].substring(0, state[i].length - 1).split(" ")
            for (j in 1..<toks.size) {
                if (toks[j].startsWith("generator")) {
                    initialState[elements.indexOf(toks[j-1]), 0] = i
                } else if (toks[j].startsWith("microchip")) {
                    val element = toks[j-1].split("-")[0]
                    initialState[elements.indexOf(element), 1] = i
                }
            }
        }

        var distances = mutableMapOf<IntMatrix, Int>()
        distances.put(initialState, 0)
        var queue = ArrayDeque<IntMatrix>()
        queue.addLast(initialState)
        var solutions = ArrayDeque<IntMatrix>()

        for (minFloor in 0..<state.size) {
            distances.keys.retainAll(queue)

            while (queue.size > 0) {
                var curr = queue.removeFirst()
                if ((0..<curr.rows).all { r -> (0..<curr.cols).all { c -> curr[r, c] > minFloor } }) {
                    solutions.addLast(curr)
                    continue
                }

                for (step in getNextSteps(curr, minFloor)) {
                    val next = curr + step
                    if (!distances.containsKey(next) && validate(next)) {
                        distances.put(next, distances.getValue(curr) + 1)
                        queue.addLast(next)
                    }
                }
            }

            println("All at floor $minFloor")

            queue = solutions
            solutions = ArrayDeque<IntMatrix>()
        }

        for ((state, dist) in distances) {
            println(state)
            println(dist)
            println()
        }

        return res
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part2(state: State11): Long {
        var res = 0L

        // Count microchips
        var elements = mutableListOf("", "elerium", "dilithium")
        for (line in state) {
            val toks = line.substring(0, line.length - 1).split(" ")
            for (i in 1..<toks.size) {
                if (toks[i].startsWith("generator")) {
                    elements.add(toks[i-1])
                }
            }
        }

        // Initialize grid
        val initialState = IntMatrix(elements.size, 2) { r, c -> 0 }
        initialState[0, 0] = 0 // elevator
        initialState[0, 1] = state.size - 1 // I have a spare value, so use it for floors
        for (i in 0..<state.size) {
            val toks = state[i].substring(0, state[i].length - 1).split(" ")
            for (j in 1..<toks.size) {
                if (toks[j].startsWith("generator")) {
                    initialState[elements.indexOf(toks[j-1]), 0] = i
                } else if (toks[j].startsWith("microchip")) {
                    val element = toks[j-1].split("-")[0]
                    initialState[elements.indexOf(element), 1] = i
                }
            }
        }

        var distances = mutableMapOf<IntMatrix, Int>()
        distances.put(initialState, 0)
        var queue = ArrayDeque<IntMatrix>()
        queue.addLast(initialState)
        var solutions = ArrayDeque<IntMatrix>()

        for (minFloor in 0..<state.size) {
            distances.keys.retainAll(queue)

            while (queue.size > 0) {
                var curr = queue.removeFirst()
                if ((0..<curr.rows).all { r -> (0..<curr.cols).all { c -> curr[r, c] > minFloor } }) {
                    solutions.addLast(curr)
                    continue
                }

                for (step in getNextSteps(curr, minFloor)) {
                    val next = curr + step
                    if (!distances.containsKey(next) && validate(next)) {
                        distances.put(next, distances.getValue(curr) + 1)
                        queue.addLast(next)
                    }
                }
            }

            println("All at floor $minFloor")

            queue = solutions
            solutions = ArrayDeque<IntMatrix>()
        }

        for ((state, dist) in distances) {
            println(state)
            println(dist)
            println()
        }

        return res
    }

    fun isSolution(state: IntMatrix): Boolean {
        for (r in 0..<state.rows) {
            for (c in 0..<state.cols) {
                if (state[r, c] != state[0, 1]) {
                    return false
                }
            }
        }
        return true
    }

    fun validate(state: IntMatrix): Boolean {
        for (i in 1..<state.rows) {
            if (state[i, 0] != state[i, 1]) {
                for (j in 1..<state.rows) {
                    if (i != j && state[i, 1] == state[j, 0]) {
                        // Generator j wrecks microchip i
                        return false
                    }
                }
            }
        }
        // Survived the check, so it's valid
        return true
    }

    fun getNextSteps(state: IntMatrix, minFloor: Int): List<IntMatrix> {
        val available = mutableListOf<IntVector>()
        for (r in 1..<state.rows) {
            for (c in 0..<state.cols) {
                if (state[r, c] == state[0, 0]) {
                    available.add(IntVector.of(r, c))
                }
            }
        }

        val steps = mutableListOf<IntMatrix>()
        for (i in 0..<available.size) {
            for (j in 0..<available.size) {
                val step = IntMatrix(state.rows, state.cols) { r, c -> 0 }
                step[0, 0] = 1
                step[available[i]] = 1
                step[available[j]] = 1
                if (state[0, 0] != state[0, 1]) steps.add(step)
                if (state[0, 0] > minFloor && i == j) steps.add(step * -1)
            }
        }

        return steps
    }
}