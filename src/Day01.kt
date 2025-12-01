private const val EXPECTED_1 = 3
private const val EXPECTED_2 = 6

private class Day01(isTest: Boolean) : Solver(isTest) {
    fun part1(): Any {
        var pos = 50
        var count = 0
        for (line in readAsLines()) {
            var amount = line.substring(1).toInt() % 100
            if (line[0] == 'L') amount = -amount
            pos = (pos + amount + 100) % 100
            if (pos == 0) count ++
        }
        return count
    }

    fun part2(): Any {
        var pos = 50
        var count = 0
        for (line in readAsLines()) {
            var amount = line.substring(1).toInt().also { count += it / 100 } % 100
            if (line[0] == 'L') amount = -amount
            val oldpos = pos
            pos = (pos + amount + 100) % 100
            if (pos == 0) count ++
            else if (oldpos != 0 && (oldpos + amount < 0 || oldpos + amount >= 100)) count ++
        }
        return count
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