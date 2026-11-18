package com.adventofcode

import kotlin.reflect.full.*

fun main(args: Array<String>) {
    var actualArgs = args
    if (args.size != 3) {
        actualArgs = arrayOf("2024", "18", "/home/jmeberlein/git/advent-of-code/src/main/resources/input/2024/18_test.txt")
    }
    val state = Class.forName("com.adventofcode.aoc${actualArgs[0]}.State${actualArgs[1]}").kotlin.primaryConstructor!!.call(actualArgs[2])
    val runner = Class.forName("com.adventofcode.aoc${actualArgs[0]}.Day${actualArgs[1]}").kotlin.primaryConstructor!!.call()
    println(runner::class.members.filter { it.name == "part1" }[0].call(runner, state))
    println(runner::class.members.filter { it.name == "part2" }[0].call(runner, state))
}
