package com.adventofcode

import com.adventofcode.util.File
import com.adventofcode.aoc2025.*

fun main() {
    val state = State02()
    // val state = File("/home/jmeberlein/git/advent-of-code/src/main/resources/input/2025/02.txt")
    val runner = Day02()
    println(runner.part2(state))
}