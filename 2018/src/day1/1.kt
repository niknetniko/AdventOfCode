package day1

import common.Problem
import java.io.File
import kotlin.system.measureTimeMillis

// Will optimize speed, i.e. read whole file, but could be a stream as well.

fun asInts(file: File) : Collection<Int> {
    return file.readLines()
        .map { it.toInt() }
}

fun <T: Any> cycle(xs: Sequence<T>): Sequence<T> {
    var current = xs.iterator()
    return generateSequence {
        if (!current.hasNext()) {
            current = xs.iterator()
        }
        current.next()
    }
}

object Day1: Problem {

    override fun partI(input: File): Any {
        return asInts(input)
            .sum()
    }

    override fun partII(input: File): Any {
        val changes: Sequence<Int> = cycle(asInts(input).asSequence())
        var current = 0
        val occurred = HashSet<Int>()
        for (change in changes) {
            //println("Adding $change to $current")
            current += change
            if (current in occurred) {
                return current
            }
            occurred += current
        }
        error("Weird")
    }

}

fun main() {
    val l = measureTimeMillis {
        common.run(Day1)
    }
    println("Took $l millis")
}