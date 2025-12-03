import kotlin.math.max

private const val EXPECTED_1 = 357
private const val EXPECTED_2 = 3121910778619

private class Day03(isTest: Boolean) : Solver(isTest) {
    fun part1(): Any {
        return readAsLines().sumOf {
            val digits = it.map { it - '0' }
            var maxDigit = digits.last()
            var result = 0
            for (i in digits.size - 2 downTo 0) {
                result = max(result, digits[i] * 10 + maxDigit)
                maxDigit = max(maxDigit, digits[i])
            }
            result
        }
    }

    fun part2(): Any {
        fun pow10(exp: Int) = (0..<exp).fold(1L) { acc, _ -> acc * 10 }

        return readAsLines().sumOf {
            val digits = it.map { (it - '0').toLong() }
            val maxDigits = Array(12) { -1L }
            var result = 0L
            for (i in digits.indices.reversed()) {
                for (j in 11 downTo 0) {
                    if (j > 0 && maxDigits[j - 1] < 0) continue
                    maxDigits[j] = max(
                        maxDigits[j],
                        if (j != 0) digits[i] * pow10(j) + maxDigits[j - 1] else digits[i],
                    )
                }
                result = max(result, maxDigits[11])
            }
            result
        }
    }
}


fun main() {
    val testInstance = Day03(true)
    val instance = Day03(false)

    testInstance.part1().let { check(it == EXPECTED_1) { "part1: $it != $EXPECTED_1" } }
    println("part1 ANSWER: ${instance.part1()}")
    testInstance.part2().let {
        check(it == EXPECTED_2) { "part2: $it != $EXPECTED_2" }
        println("part2 ANSWER: ${instance.part2()}")
    }
}