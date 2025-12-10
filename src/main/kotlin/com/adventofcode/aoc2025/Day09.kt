package com.adventofcode.aoc2025

import org.openjdk.jmh.annotations.*
import com.adventofcode.util.File
import com.adventofcode.util.Vector
import java.util.concurrent.TimeUnit
import kotlin.math.max
import kotlin.math.min
import kotlin.math.abs

fun IntRange.size():Int = last - first + 1
fun IntRange.overlaps(other: IntRange): Boolean =
    max(first, other.first) <= min(last, other.last)

class Rectangle(val r: IntRange, val c: IntRange) {
    val area: Long =
        r.size().toLong() * c.size()

    fun overlaps(other: Rectangle): Boolean =
        r.overlaps(other.r) && c.overlaps(other.c)

    fun inner(): Rectangle =
        Rectangle(
            r.first + 1..<r.last,
            c.first + 1..<c.last
        )

    companion object {
        fun of(a: Vector, b: Vector): Rectangle =
            Rectangle(
                min(a[0], b[0]).toInt()..max(a[0], b[0]).toInt(),
                min(a[1], b[1]).toInt()..max(a[1], b[1]).toInt(),
            )
    }
}


@State(Scope.Thread)
open class State09(fileName: String = "src/main/resources/input/2025/09.txt") : File(fileName) {
    val points = List<Vector>(this.size) { i ->
        val tok = this[i].split(",")
        Vector.of(tok[0].toDouble(), tok[1].toDouble())
    }
}

open class Day09 {
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part1(state: State09): Long {
        var res = 0L
        for (i in 0..<state.points.size) {
            for (j in (i+1)..<state.points.size) {
                val vec = state.points[i] - state.points[j] + 1
                // println("${state.points[i]} and ${state.points[j]} have area ${vec[0] * vec[1]}")
                if (abs(vec[0] * vec[1]) > res) {
                    res = abs(vec[0] * vec[1]).toLong()
                }
            }
        }
        return res
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    fun part2(state: State09): Long {
        val rectangles: List<Rectangle> = state.points.flatMapIndexed { index, left ->
            state.points.drop(index + 1).map { right ->
                Rectangle.of(left, right)
            }
        }.sortedByDescending { it.area }

        val lines: List<Rectangle> = (state.points + state.points.first())
            .zipWithNext()
            .map { (left, right) -> Rectangle.of(left, right) }

        val max = rectangles.first { rectangle ->
            val inner = rectangle.inner()
            lines.none { line -> line.overlaps(inner) }
        }
        return max.area
    }
}
