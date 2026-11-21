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

        while (queue.size > 0) {
            var curr = queue.removeFirst()

            if (isSolution(curr)) {
                // return distances.getValue(curr).toLong()
                break
            }

            if (noneBelowElevator(curr)) {
                val floor = curr[0, 0]
                distances = pruneDistances(floor, distances)
                queue = pruneQueue(floor, queue)
                curr -= floor
            }

            for (step in getNextSteps(curr)) {
                val next = curr + step
                if (validate(next)) {
                    if (!distances.containsKey(next)) {
                        distances.put(next, distances.getValue(curr) + 1)
                        queue.addLast(next)
                    }
                }
            }
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

        // // Count microchips
        // var elements = mutableListOf("", "elerium", "dilithium")
        // for (line in state) {
        //     val toks = line.substring(0, line.length - 1).split(" ")
        //     for (i in 1..<toks.size) {
        //         if (toks[i].startsWith("generator")) {
        //             elements.add(toks[i-1])
        //         }
        //     }
        // }

        // // Initialize grid
        // val initialState = IntMatrix(elements.size + 2, 2) { r, c -> 0 }
        // initialState[0, 0] = 0 // elevator
        // initialState[0, 1] = state.size - 1 // I have a spare value, so use it for floors
        // for (i in 0..<state.size) {
        //     val toks = state[i].substring(0, state[i].length - 1).split(" ")
        //     for (j in 1..<toks.size) {
        //         if (toks[j].startsWith("generator")) {
        //             initialState[elements.indexOf(toks[j-1]), 0] = i
        //         } else if (toks[j].startsWith("microchip")) {
        //             val element = toks[j-1].split("-")[0]
        //             initialState[elements.indexOf(element), 1] = i
        //         }
        //     }
        // }

        // val distances = mutableMapOf<IntMatrix, Int>()
        // distances.put(initialState, 0)
        // val queue = ArrayDeque<IntMatrix>()
        // queue.addLast(initialState)

        // while (queue.size > 0) {
        //     val curr = queue.removeFirst()
        //     if (isSolution(curr)) {
        //         return distances.getValue(curr).toLong()
        //         // break
        //     }

        //     for (step in getNextSteps(curr)) {
        //         val next = curr + step
        //         if (!distances.containsKey(next) && validate(next)) {
        //             distances.put(next, distances.getValue(curr) + 1)
        //             queue.addLast(next)
        //         }
        //     }
        // }

        // for ((state, dist) in distances) {
        //     println(state)
        //     println(dist)
        //     println()
        // }

        return res
    }

    fun noneBelowElevator(state: IntMatrix): Boolean {
        if (state[0, 0] == 0) return false
        for (r in 1..<state.rows) {
            for (c in 1..<state.cols) {
                if (state[r, c] < state[0, 0]) {
                    return false
                }
            }
        }
        return true
    }

    fun pruneDistances(floor: Int, distances: MutableMap<IntMatrix, Int>): MutableMap<IntMatrix, Int> {
        println("Before pruning floor $floor")
        for ((state, dist) in distances) {
            println(state)
            println(dist)
            println()
        }

        val res = mutableMapOf<IntMatrix, Int>()
        for ((state, dist) in distances) {
            val tmp = state - floor
            if ((0..<state.rows).all { r -> (0..<state.cols).all { c -> tmp[r, c] >= 0 } }) {
                res.put(tmp, dist)
            }
        }

        println("After pruning")
        for ((state, dist) in res) {
            println(state)
            println(dist)
            println()
        }
        println("---")

        return res
    }

    fun pruneQueue(floor: Int, queue: ArrayDeque<IntMatrix>): ArrayDeque<IntMatrix> {
        println("Before pruning floor $floor")
        for (state in queue) {
            println(state)
            println()
        }
        
        val res = ArrayDeque<IntMatrix>()
        for (state in queue) {
            val tmp = state - floor
            if ((0..<state.rows).all { r -> (0..<state.cols).all { c -> tmp[r, c] >= 0 } }) {
                res.addLast(tmp)
            }
        }

        println("After pruning")
        for (state in res) {
            println(state)
            println()
        }
        println("---")

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
        if (state[0, 0] < 0 || state[0, 0] > state[0, 1]) {
            // Elevator's on an invalid floor
            return false
        }
        if ((0..<state.rows).any { r -> (0..<state.cols).any { c -> state[r, c] < 0 } }) {
            return false
        }
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

    fun getNextSteps(state: IntMatrix): List<IntMatrix> {
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
                if (state[0, 0] != 0) steps.add(step * -1)
            }
        }

        return steps
    }
}