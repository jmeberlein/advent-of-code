package com.adventofcode.aoc2015

import org.openjdk.jmh.annotations.*
import com.adventofcode.util.File
import java.util.concurrent.TimeUnit
import kotlinx.serialization.json.*
// import kotlin.text.toLongOrNull

@State(Scope.Thread)
open class State12(fileName: String = "src/main/resources/input/2015/12.txt") : File(fileName)

open class Day12 {
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part1(state: State12): Long {
        var res = 0L
        var queue = ArrayDeque<JsonElement>()
        queue.add(Json.parseToJsonElement(state[0]))
        while (queue.isNotEmpty()) {
            var curr = queue.removeFirst()
            if (curr is JsonPrimitive) {
                if (curr.jsonPrimitive.longOrNull != null) {
                    res += curr.jsonPrimitive.long
                }
            } else if (curr is JsonArray) {
                curr.jsonArray.forEach { v -> queue.add(v) }
            } else if (curr is JsonObject) {
                curr.jsonObject.forEach { (k,v)->
                    if (k.toLongOrNull() != null) {
                        res += k.toLong()
                    }
                    queue.add(v)
                }
            }
        }
        return res
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part2(state: State12): Long {
        var res = 0L
        var queue = ArrayDeque<JsonElement>()
        queue.add(Json.parseToJsonElement(state[0]))
        while (queue.isNotEmpty()) {
            var curr = queue.removeFirst()
            if (curr is JsonPrimitive) {
                if (curr.jsonPrimitive.longOrNull != null) {
                    res += curr.jsonPrimitive.long
                }
            } else if (curr is JsonArray) {
                curr.jsonArray.forEach { v -> queue.add(v) }
            } else if (curr is JsonObject && curr.jsonObject.values.none { el->
                    (el is JsonPrimitive) && el.jsonPrimitive.isString && el.jsonPrimitive.content == "red"
                }) {
                curr.jsonObject.forEach { (k,v)->
                    if (k.toLongOrNull() != null) {
                        res += k.toLong()
                    }
                    queue.add(v)
                }
            }
        }
        return res
    }
}
