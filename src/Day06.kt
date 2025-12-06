private const val EXPECTED_1 = 4277556L
private const val EXPECTED_2 = 3263827L

private class Day06(isTest: Boolean) : Solver(isTest) {
    fun part1(): Any {
        val (numbers, ops) = readAsLines().let {
            it.dropLast(1).map { it.splitLongs() } to
                    it.last().split(" ").filter { !it.isEmpty() }
        }

        return ops.withIndex().sumOf { (index, op) ->
            val nums = numbers.map { it[index] }
            if (op == "+") {
                nums.sum()
            } else {
                // op == "*"
                nums.reduce { acc, i -> acc * i }
            }
        }
    }

    fun part2(): Any {
        val (field, opLine) = readAsLines().let {
            it.dropLast(1) to it.last()
        }

        return opLine.withIndex().sumOf { (index, ch) ->
            if (ch != ' ') {
                var end = index
                while (end < field[0].length && field.any { it[end].isDigit() }) end++
                val nums = mutableListOf<Long>()

                for (i in end - 1 downTo index) {
                    nums.add(field.map { it[i] }.filter { it.isDigit() }.joinToString("").toLong())
                }
                if (ch == '+') {
                    nums.sum()
                } else {
                    nums.reduce { acc, i -> acc * i }
                }
            } else {
                0L
            }
        }
    }
}

fun main() {
    val testInstance = Day06(true)
    val instance = Day06(false)

    testInstance.part1().let { check(it == EXPECTED_1) { "part1: $it != $EXPECTED_1" } }
    println("part1 ANSWER: ${instance.part1()}")
    testInstance.part2().let {
        check(it == EXPECTED_2) { "part2: $it != $EXPECTED_2" }
        println("part2 ANSWER: ${instance.part2()}")
    }
}