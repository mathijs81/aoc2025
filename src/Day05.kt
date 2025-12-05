private const val EXPECTED_1 = 3
private const val EXPECTED_2 = 14L

private class Day05(isTest: Boolean) : Solver(isTest) {
    fun part1(): Any {
        val parts = readAsString().split("\n\n")
        val ranges = parts[0].split("\n").map {
            it.split("-").map { it.toLong() }
        }

        val queries = parts[1].split("\n").map { it.toLong() }
        return queries.count { query -> ranges.any { query in it[0] .. it[1]} }
    }

    fun part2(): Any {
        val parts = readAsString().split("\n\n")
        val ranges = parts[0].split("\n").map {
            it.split("-").map { it.toLong() }
        }

        val changes = ranges.flatMap { listOf(it[0] to 1, (it[1] + 1) to -1) }.sortedBy { it.first }
        var inRange = 0
        var result = 0L
        var lastNumber = 0L
        for ((num, type) in changes) {
            val diff = num - lastNumber
            if (inRange > 0) {
                result += diff
            }
            lastNumber = num
            inRange += type
            check(result >= 0)
        }
        return result
    }
}


fun main() {
    val testInstance = Day05(true)
    val instance = Day05(false)

    testInstance.part1().let { check(it == EXPECTED_1) { "part1: $it != $EXPECTED_1" } }
    println("part1 ANSWER: ${instance.part1()}")
    testInstance.part2().let {
        check(it == EXPECTED_2) { "part2: $it != $EXPECTED_2" }
        println("part2 ANSWER: ${instance.part2()}")
    }
}