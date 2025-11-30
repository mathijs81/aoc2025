import kotlin.math.absoluteValue

private const val EXPECTED_1 = 11L
private const val EXPECTED_2 = 31L

private class Day01(isTest: Boolean) : Solver(isTest) {
    fun part1(): Long {
        val list = readAsLines().map { it.splitInts() }
        val firstCol = list.map { it[0] }.sorted()
        val secondCol = list.map { it[1] }.sorted()

        val diff = firstCol.zip(secondCol).sumInt { (it.first - it.second).absoluteValue }
        return diff.toLong()
    }

    fun part2(): Any {
        val list = readAsLines().map { it.splitInts() }
        val firstCol = list.map { it[0] }.sorted()
        val secondCol = list.map { it[1] }.sorted()
        val secondFrequency = secondCol.groupBy { it }.mapValues { it.value.size }

        var sum = 0L

        for (num in firstCol) {
            sum += num * (secondFrequency[num] ?: 0)
        }

        return sum
    }
}


fun main() {
    val testInstance = Day01(true)
    val instance = Day01(false)

    testInstance.part1().let { check(it == EXPECTED_1) { "part1: $it != $EXPECTED_1" } }
    println("part1 ANSWER: ${instance.part1()}")
    testInstance.part2().let {
        check(it == EXPECTED_2) { "part2: $it != $EXPECTED_2" }
        println("part2 ANSWER: ${instance.part2()}")
    }
}