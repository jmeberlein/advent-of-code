package com.adventofcode

import kotlin.reflect.full.*
import com.adventofcode.util.*

fun main(args: Array<String>) {
    val state = Class.forName("com.adventofcode.aoc${args[0]}.State${args[1]}").kotlin.primaryConstructor!!.call(args[2])
    val runner = Class.forName("com.adventofcode.aoc${args[0]}.Day${args[1]}").kotlin.primaryConstructor!!.call()
    println(runner::class.members.filter { it.name == "part1" }[0].call(runner, state))
    println(runner::class.members.filter { it.name == "part2" }[0].call(runner, state))
}
