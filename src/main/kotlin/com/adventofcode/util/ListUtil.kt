package com.adventofcode.util

fun <T> List<T>.allPermutations(): Iterator<List<T>> {
    return object: Iterator<List<T>> {
        private var indices = IntArray(size) { it }
        private var direction = BooleanArray(size) { false }
        private var hasNext = true

        override fun hasNext(): Boolean {
            return hasNext
        }

        override fun next(): List<T> {
            val res = List(size) { get(indices[it]) }
            val idx = getMobileIndex()
            if (idx == -1) {
                hasNext = false
                return res
            }
            
            val mobile = indices[idx]
            if (direction[idx]) {
                indices[idx] = indices[idx+1].also { indices[idx+1] = indices[idx] }
                direction[idx] = direction[idx+1].also { direction[idx+1] = direction[idx] }
            } else {
                indices[idx] = indices[idx-1].also { indices[idx-1] = indices[idx] }
                direction[idx] = direction[idx-1].also { direction[idx-1] = direction[idx] }
            }
            for (i in indices.indices) {
                if (indices[i] > mobile) {
                    direction[i] = !direction[i]
                }
            }
            return res
        }

        private fun getMobileIndex(): Int {
            var max = -1
            var maxIdx = -1

            for (i in 0..<size) {
                if ((direction[i] && i < indices.lastIndex && indices[i] > indices[i+1] && indices[i] > max) ||
                    (!direction[i] && i > 0 && indices[i] > indices[i-1] && indices[i] > max)) {
                    max = indices[i]
                    maxIdx = i
                }
            }

            return maxIdx
        }
    }
}