package com.adventofcode.aoc2025

import org.openjdk.jmh.annotations.*
import com.adventofcode.util.File
import com.adventofcode.util.Matrix
import java.util.concurrent.TimeUnit
import kotlin.math.max
import kotlin.math.min
import kotlin.math.abs

@State(Scope.Thread)
open class State11(fileName: String = "src/main/resources/input/2025/11.txt") : File(fileName) {}

open class Day11 {
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part1(state: State11): Long {
        var res = 0L
        var devices = mutableSetOf<String>()
        var deviceIDs = mutableMapOf<String, Int>()
        var id = 0
        for (line in state) {
            for (device in line.split(Regex(":? "))) {
                if (!devices.contains(device)) {
                    devices.add(device)
                    deviceIDs.put(device, id++)
                }
            }
        }
        var paths = Matrix(devices.size) { _,_ -> 0.0 }
        for (line in state) {
            var toks = line.split(Regex(":? "))
            for (i in 1..<toks.size) {
                paths[deviceIDs[toks[0]]!!, deviceIDs[toks[i]]!!] = 1.0
            }
        }
        for (k in 0..<devices.size) {
            for (i in 0..<devices.size) {
                for (j in 0..<devices.size) {
                    if (i != j && j != k && k != i) {
                        paths[i, j] += paths[i, k] * paths[k, j]
                    }
                }
            }
        }
        return paths[deviceIDs["you"]!!, deviceIDs["out"]!!].toLong()
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part2(state: State11): Long {
        var res = 0L
        var devices = mutableSetOf<String>()
        var deviceIDs = mutableMapOf<String, Int>()
        var id = 0
        for (line in state) {
            for (device in line.split(Regex(":? "))) {
                if (!devices.contains(device)) {
                    devices.add(device)
                    deviceIDs.put(device, id++)
                }
            }
        }
        var paths = Matrix(devices.size) { _,_ -> 0.0 }
        for (line in state) {
            var toks = line.split(Regex(":? "))
            for (i in 1..<toks.size) {
                paths[deviceIDs[toks[0]]!!, deviceIDs[toks[i]]!!] = 1.0
            }
        }
        for (k in 0..<devices.size) {
            for (i in 0..<devices.size) {
                for (j in 0..<devices.size) {
                    if (i != j && j != k && k != i) {
                        paths[i, j] += paths[i, k] * paths[k, j]
                    }
                }
            }
        }
        return paths[deviceIDs["svr"]!!, deviceIDs["dac"]!!].toLong() *
                paths[deviceIDs["dac"]!!, deviceIDs["fft"]!!].toLong() *
                paths[deviceIDs["fft"]!!, deviceIDs["out"]!!].toLong() +
                paths[deviceIDs["svr"]!!, deviceIDs["fft"]!!].toLong() *
                paths[deviceIDs["fft"]!!, deviceIDs["dac"]!!].toLong() *
                paths[deviceIDs["dac"]!!, deviceIDs["out"]!!].toLong()
    }
}