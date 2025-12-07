private const val EXPECTED_1 = 21
private const val EXPECTED_2 = 40L

private class Day07(isTest: Boolean) : Solver(isTest) {
    val field = readAsLines()
    val Y = field.size
    val X = field[0].length

    fun part1(): Any {
        val visited = Array(Y) { BooleanArray(X) { false } }

        fun splits(x: Int, y: Int): Int {
            if (y >= Y || x < 0 || x >= X) return 0
            if (visited[y][x]) return 0
            visited[y][x] = true
            if (field[y][x] == '^') {
                return 1 + splits(x - 1, y) + splits(x + 1, y)
            }
            return splits(x, y + 1)
        }
        return splits(field[0].indexOf('S'), 0)
    }

    fun part2(): Any {
        val count = Array(Y) { y -> LongArray(X) { if (y == Y-1) 1L else -1L } }

        fun countPaths(x: Int, y: Int): Long {
            if (y >= Y || x < 0 || x >= X) return 0
            if (count[y][x] >= 0) return count[y][x]
            if (field[y][x] == '^') {
                count[y][x] = countPaths(x - 1, y) + countPaths(x + 1, y)
            } else {
                count[y][x] = countPaths(x, y + 1)
            }
            return count[y][x]
        }
        return countPaths(field[0].indexOf('S'), 0)
    }
}


fun main() {
    val testInstance = Day07(true)
    val instance = Day07(false)

    testInstance.part1().let { check(it == EXPECTED_1) { "part1: $it != $EXPECTED_1" } }
    println("part1 ANSWER: ${instance.part1()}")
    testInstance.part2().let {
        check(it == EXPECTED_2) { "part2: $it != $EXPECTED_2" }
        println("part2 ANSWER: ${instance.part2()}")
    }
}