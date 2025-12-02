private const val EXPECTED_1 = 1227775554L
private const val EXPECTED_2 = 4174379265L

private class Day02_alt(isTest: Boolean) : Solver(isTest) {
    fun calc(regex: String): Long {
        val re = Regex(regex)
        return readAsLines().first().replace('-', ' ').splitLongs().chunked(2).sumOf {
            (it[0]..it[1]).sumOf { candidate ->
                if (re.matches(candidate.toString())) candidate.toLong() else 0L
            }
        }
    }

    fun part1() = calc("(.+)\\1")
    fun part2() = calc("(.+)\\1+")
}


fun main() {
    val testInstance = Day02_alt(true)
    val instance = Day02_alt(false)

    testInstance.part1().let { check(it == EXPECTED_1) { "part1: $it != $EXPECTED_1" } }
    println("part1 ANSWER: ${instance.part1()}")
    testInstance.part2().let {
        check(it == EXPECTED_2) { "part2: $it != $EXPECTED_2" }
        println("part2 ANSWER: ${instance.part2()}")
    }
}