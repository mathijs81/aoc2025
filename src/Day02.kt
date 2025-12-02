private const val EXPECTED_1 = 1227775554L
private const val EXPECTED_2 = 4174379265L

fun getMult(a: Long): Long {
    var result = 1L
    var num = a
    while (num > 0) {
        result *= 10
        num /= 10
    }
    return result
}

class Seq(val pat: Long, val mult: Long = getMult(pat)) {
    fun value() = pat + mult * pat
    fun values(max: Long): List<Long> {
        if (pat == 0L) return listOf(0L)
        val result = mutableListOf<Long>()
        var value = pat
        while (value <= max) {
            if (value != pat)
                result.add(value)
            if (max / mult < value) break
            value = value * mult + pat
        }
        return result
    }
    fun next(): Seq {
        if (pat + 1 >= mult) {
            return Seq(pat + 1, mult * 10)
        } else {
            return Seq(pat + 1, mult)
        }
    }
}

private class Day02(isTest: Boolean) : Solver(isTest) {
    fun part1(): Any {
        var invalid = 0L
        val input = readAsLines().first().replace('-', ' ').splitLongs().chunked(2)
        for (pair in input) {
            val (a, b) = pair
            var iter = Seq(a.toString().let {
                if (it.length <= 1) 0L else it.substring(0, it.length/2).toLong()
            })
            while (true) {
                val value = iter.value()
                if (value > b) {
                    break
                }
                if (value >= a && value <= b) {
                    invalid += value
                }
                iter = iter.next()
            }
        }
        return invalid
    }

    fun part2(): Any {
        var invalid = 0L
        val input = readAsLines().first().replace('-', ' ').splitLongs().chunked(2)
        val seen = mutableSetOf<Long>()
        for (pair in input) {
            val (a, b) = pair
            var iter = Seq(a.toString().let {
                1L
            })
            while (true) {
                val values = iter.values(b)
                if (values.isEmpty()) {
                    break
                }
                for (value in values) {
                    if (value >= a && seen.add(value)) {
                        invalid += value
                    }
                }
                iter = iter.next()
            }
        }
        return invalid
    }
}


fun main() {
    val testInstance = Day02(true)
    val instance = Day02(false)

    testInstance.part1().let { check(it == EXPECTED_1) { "part1: $it != $EXPECTED_1" } }
    println("part1 ANSWER: ${instance.part1()}")
    testInstance.part2().let {
        check(it == EXPECTED_2) { "part2: $it != $EXPECTED_2" }
        println("part2 ANSWER: ${instance.part2()}")
    }
}