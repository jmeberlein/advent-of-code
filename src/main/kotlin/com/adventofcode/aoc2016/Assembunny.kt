package com.adventofcode.aoc2016

class Assembunny(arr: Iterable<String>) {
    val commands = arr.map { it.split(" ").toMutableList() }.toList()
    var idx = 0
    var a = 0
    var b = 0
    var c = 0
    var d = 0

    fun run() {
        idx = 0
        while (idx < commands.size) {
            when (commands[idx][0]) {
                "inc" -> inc(commands[idx][1])
                "dec" -> dec(commands[idx][1])
                "tgl" -> tgl(commands[idx][1])
                "jnz" -> jnz(commands[idx][1], commands[idx][2])
                "cpy" -> cpy(commands[idx][1], commands[idx][2])
            }
            idx++
        }
    }

    fun parse(op: String): Int {
        return when (op) {
            "a" -> a
            "b" -> b
            "c" -> c
            "d" -> d
            else -> op.toInt()
        }
    }

    fun inc(register: String) {
        when (register) {
            "a" -> a++
            "b" -> b++
            "c" -> c++
            "d" -> d++
        }
    }

    fun dec(register: String) {
        when (register) {
            "a" -> a--
            "b" -> b--
            "c" -> c--
            "d" -> d--
        }
    }

    fun tgl(op: String) {
        val target = idx + parse(op)
        if (target >= 0 && target < commands.size) {
            val cmd = commands[idx + parse(op)]
            cmd[0] = when (cmd[0]) {
                "inc" -> "dec"
                "jnz" -> "cpy"
                "cpy" -> "jnz"
                else  -> "inc"
            }
        }
    }

    fun jnz(op0: String, op1: String) {
        if (parse(op0) != 0) {
            idx += parse(op1) - 1
        }
    }

    fun cpy(op0: String, op1: String) {
        when (op1) {
            "a" -> a = parse(op0)
            "b" -> b = parse(op0)
            "c" -> c = parse(op0)
            "d" -> d = parse(op0)
        }
    }
}