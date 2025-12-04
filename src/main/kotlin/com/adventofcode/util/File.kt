package com.adventofcode.util

import org.openjdk.jmh.annotations.*

open class File {
    public var lines: List<String>// = ArrayList<>()

    constructor(fileName: String) {
        this.lines = java.io.File(fileName).readLines()
    }
}