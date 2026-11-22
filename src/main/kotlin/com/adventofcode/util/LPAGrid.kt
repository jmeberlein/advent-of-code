package com.adventofcode.util

import java.util.PriorityQueue
import kotlin.math.min

class LPAGrid(
        val rows: Int, 
        val cols: Int, 
        val start: IntVector, 
        val dest: IntVector, 
        useHeuristic: Boolean = false, 
        init: (Int, Int) -> Boolean
) {
    val INFINITY = rows * cols + 1
    val walls = Grid<Boolean>(rows, cols, init)
    val reported = IntMatrix(rows, cols) { _, _ -> INFINITY }
    val expected = IntMatrix(rows, cols) { r, c -> if (r == start[0] && c == start[1]) 0 else INFINITY }
    val heuristic = IntMatrix(rows, cols) { r, c -> if (useHeuristic) (dest - IntVector.of(r, c)).magnitude(1.0).toInt() else 0 }
    val queue = PriorityQueue<IntVector>(compareBy<IntVector> { 
        min(expected[it].toInt(), reported[it].toInt()) + heuristic[it].toInt()
    }.thenBy {
        min(expected[it].toInt(), reported[it].toInt())
    })
    val NEIGHBORS = arrayOf(IntVector.UP, IntVector.DOWN, IntVector.LEFT, IntVector.RIGHT)

    init {
        queue.offer(start)
        update()
    }

    fun getScore(vec: IntVector): Int {
        return min(expected[vec].toInt(), reported[vec].toInt()) + heuristic[vec].toInt()
    }

    fun flipWall(vec: IntVector) {
        walls[vec] = !walls[vec]
        reported[vec] = INFINITY
        expected[vec] = INFINITY
        if (!walls[vec]) {
            queue.offer(vec)
        }
        NEIGHBORS.forEach { updateExpected(vec + it) }
        update()
    }

    fun update() {
        while (queue.size > 0 || (expected[dest] != reported[dest] && getScore(dest) >= getScore(queue.peek()))) {
            val curr = queue.poll()
            if (expected[curr].toInt() < reported[curr].toInt()) {
                setReported(curr, expected[curr].toInt())
            } else if (expected[curr].toInt() > reported[curr].toInt()) {
                setReported(curr, INFINITY)
            }
        }
    }

    fun setReported(vec: IntVector, dist: Int) {
        if (reported[vec] != dist && !walls[vec]) {
            reported[vec] = dist
            if (reported[vec] != expected[vec]) {
                queue.remove(vec)
                queue.offer(vec)
            }
            NEIGHBORS.forEach { updateExpected(vec + it) }
        }
    }

    fun updateExpected(vec: IntVector) {
        if (walls.contains(vec) && vec != start && !walls[vec]) {
            val minDist = NEIGHBORS.minOf { if (reported.contains(vec + it)) reported[vec + it].toInt() + 1 else INFINITY }
            if (expected[vec] != minDist) {
                expected[vec] = minDist
                queue.remove(vec)
                queue.offer(vec)
            }
        }
    }
}