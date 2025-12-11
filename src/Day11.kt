import java.io.File

private const val EXPECTED_1 = 5L
private const val EXPECTED_2 = 2L

private class Day11(isTest: Boolean) : Solver(isTest) {
    // bit of extra code to allow part2 to switch to a different test input file
    var file = super.inputFile()
    override fun inputFile() = file

    fun readEdges() = mutableMapOf<String, Set<String>>().also { edgeMap ->
        for (line in readAsLines()) {
            val (from, to) = line.split(":").map { it.trim() }
            val neighbors = to.split(" ").filter { it.isNotEmpty() }.map { it.trim() }.toSet()
            edgeMap[from] = neighbors
        }
    }

    var edges = readEdges()

    fun part1(): Any {
        val counts = mutableMapOf<String, Long>()
        counts["out"] = 1L
        fun countPaths(current: String): Long {
            counts[current]?.let { return it }
            return edges[current]!!.sumOf {
                countPaths(it)
            }.also { counts[current] = it }
        }
        return countPaths("you")
    }

    fun part2(): Any {
        if (isTest) {
            file = File(super.inputFile().absolutePath + ".2")
            edges = readEdges()
        }
        val counts = mutableMapOf<String, Long>()
        fun countPaths(current: String, bitmask_: Int): Long {
            if (current == "out") {
                return if (bitmask_ == 3) 1L else 0L
            }
            val bitmask = bitmask_ or (if (current == "fft") 1 else if (current == "dac") 2 else 0)
            val key = "$current|$bitmask"
            counts[key]?.let { return it }
            var result = 0L
            for (target in edges[current]!!) {
                result += countPaths(target, bitmask)
            }
            return result.also { counts[key] = it }
        }

        return countPaths("svr", 0)
    }
}


fun main() {
    val testInstance = Day11(true)
    val instance = Day11(false)

    testInstance.part1().let { check(it == EXPECTED_1) { "part1: $it != $EXPECTED_1" } }
    println("part1 ANSWER: ${instance.part1()}")
    testInstance.part2().let {
        check(it == EXPECTED_2) { "part2: $it != $EXPECTED_2" }
        println("Test OK")
        println("part2 ANSWER: ${instance.part2()}")
    }
}