package com.adventofcode.aoc2015

import org.openjdk.jmh.annotations.*
import com.adventofcode.util.File
import java.util.concurrent.TimeUnit

@State(Scope.Thread)
open class State07(fileName: String = "src/main/resources/input/2015/07.txt") : File(fileName)

open class Day07 {
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part1(state: State07): Long {
        var res = 0L
        var commands = mutableMapOf<String, String>()
        var outputs = mutableMapOf<String, Long>()
        state.map { s -> s.split(" -> ") }.associateByTo(commands, { it[1] }, { it[0] })
        while (commands.size != outputs.size) {
            for ((k,v) in commands) {
                if (outputs.containsKey(k)) {
                    continue
                }

                val toks = v.split(" ")
                if (toks.size == 1) {
                    val lt = if (outputs.containsKey(toks[0])) outputs[toks[0]]!! else toks[0].toLongOrNull()

                    if (lt != null) {
                        outputs[k] = lt
                    }
                } else if (toks.size == 2) {
                    val lt = if (outputs.containsKey(toks[1])) outputs[toks[1]]!! else toks[1].toLongOrNull()

                    if (lt != null) {
                        outputs[k] = lt.inv()
                    }
                } else {
                    val lt = if (outputs.containsKey(toks[0])) outputs[toks[0]]!! else toks[0].toLongOrNull()
                    val rt = if (outputs.containsKey(toks[2])) outputs[toks[2]]!! else toks[2].toLongOrNull()
                    if (lt != null && rt != null) {
                        outputs[k] = when (toks[1]) {
                            "AND" -> lt and rt
                            "OR" -> lt or rt
                            "LSHIFT" -> lt shl rt.toInt()
                            "RSHIFT" -> lt shr rt.toInt()
                            else -> 0
                        }
                    }
                }
            }
        }
        return outputs["a"]!!
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part2(state: State07): Long {
        var res = 0L
        var commands = mutableMapOf<String, String>()
        var outputs = mutableMapOf<String, Long>()
        state.map { s -> s.split(" -> ") }.associateByTo(commands, { it[1] }, { it[0] })
        for (i in 0..1) {
            while (commands.size != outputs.size) {
                for ((k,v) in commands) {
                    if (outputs.containsKey(k)) {
                        continue
                    }

                    val toks = v.split(" ")
                    if (toks.size == 1) {
                        val lt = if (outputs.containsKey(toks[0])) outputs[toks[0]]!! else toks[0].toLongOrNull()
                        
                        if (lt != null) {
                            outputs[k] = lt
                        }
                    } else if (toks.size == 2) {
                        val lt = if (outputs.containsKey(toks[1])) outputs[toks[1]]!! else toks[1].toLongOrNull()

                        if (lt != null) {
                            outputs[k] = lt.inv()
                        }
                    } else {
                        val lt = if (outputs.containsKey(toks[0])) outputs[toks[0]]!! else toks[0].toLongOrNull()
                        val rt = if (outputs.containsKey(toks[2])) outputs[toks[2]]!! else toks[2].toLongOrNull()
                        if (lt != null && rt != null) {
                            outputs[k] = when (toks[1]) {
                                "AND" -> lt and rt
                                "OR" -> lt or rt
                                "LSHIFT" -> lt shl rt.toInt()
                                "RSHIFT" -> lt shr rt.toInt()
                                else -> 0
                            }
                        }
                    }
                }
            }

            val tmp = outputs["a"]!!
            outputs.clear()
            outputs.put("b", tmp)
        }
        return outputs["b"]!!
    }

}
