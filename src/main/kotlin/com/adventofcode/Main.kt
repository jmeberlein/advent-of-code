package com.adventofcode

import com.adventofcode.util.File
import com.adventofcode.aoc2025.*

import kotlin.reflect.*
import kotlin.reflect.full.*

fun main(args: Array<String>) {
    val stateClass = Class.forName("com.adventofcode.aoc${args[0]}.State${args[1]}").kotlin
    val runnerClass = Class.forName("com.adventofcode.aoc${args[0]}.Day${args[1]}").kotlin
    val state = stateClass.primaryConstructor!!.call(args[2])
    val runner = runnerClass.primaryConstructor!!.call()
    val part1 = runner::class.members.filter { it.name == "part1" }[0]
    val part2 = runner::class.members.filter { it.name == "part2" }[0]
    println(part1.call(runner, state))
    println(part2.call(runner, state))
}
