package com.adventofcode.util

import org.openjdk.jmh.annotations.*

open class File(fileName: String) : Iterable<String> {
    private val lines: List<String> = java.io.File(fileName).readLines()
    val size = lines.size

    operator fun get(i: Int): String {
        return lines[i]
    }

    override operator fun iterator() : Iterator<String> {
        return lines.iterator()
    }
}
