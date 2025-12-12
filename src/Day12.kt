private const val EXPECTED_1 = 2
private const val EXPECTED_2 = 0


private class Day12(isTest: Boolean) : Solver(isTest) {
    fun part1(): Any {
        val parts = readAsString().split("\n\n".toRegex())
        val pieces = mutableListOf<List<Pair<Int, Int>>>()
        for (p in parts.dropLast(1)) {
            val c = mutableListOf<Pair<Int, Int>>()
            p.substringAfter(":\n").lines().withIndex().forEach { (y, line) ->
                line.forEachIndexed { x, ch ->
                    if (ch == '#') {
                        c.add((x - 1) to (y - 1))
                    }
                }
            }
            pieces.add(c)
        }

        // Create flips & rotations

        val forms = mutableListOf<List<List<Pair<Int, Int>>>>()

        fun List<Pair<Int, Int>>.hash(): Long {
            var h = 0L
            for (p in this.sortedBy { it.first * 100 + it.second }) {
                h = h * 31 + (p.first + 10L) * 17 + (p.second + 10L)
            }
            return h
        }

        for (p in pieces) {
            val hashes = mutableSetOf<Long>()
            val pForms = mutableListOf<List<Pair<Int, Int>>>()
            var currentForm = p.toMutableList()
            repeat(4) {
                for (i in 0..<currentForm.size) {
                    currentForm[i] = (-currentForm[i].second) to (currentForm[i].first)
                }

                repeat(2) {
                    for (i in 0..<currentForm.size) {
                        currentForm[i] = (currentForm[i].first) to (-currentForm[i].second)
                    }

                    val hash = currentForm.hash()
                    if (hashes.add(hash)) {
                        pForms.add(currentForm.toList())
                    }
                }
            }
            forms.add(pForms)
        }

        return parts.last().split("\n").sumOf { line ->
            val (dim, counts) = line.splitInts().let { it.take(2) to it.drop(2) }
            val (X, Y) = dim

            val field = Array(Y) { BooleanArray(X) { false } }

            fun place(y: Int, x: Int, countsLeft: Array<Int>): Boolean {
                if (y >= Y) {
                    return countsLeft.all { it == 0 }
                }
                if (x >= X) {
                    return place(y + 1, 0, countsLeft)
                }
                val blocksToPlace =
                    countsLeft.mapIndexed { index, i -> pieces[index].size * i }.sum()
                if (blocksToPlace <= 0) return true
                if (blocksToPlace > (Y - y - 1) * X + (X - x + 1) * 2) return false

                if (x >= 1 && x < X - 1 && y >= 1 && y < Y - 1) {
                    countsLeft.forEachIndexed { index, i ->
                        if (i > 0) {
                            for (form in forms[index]) {
                                var canPlace = true
                                for (block in form) {
                                    val (dx, dy) = block
                                    val nx = x + dx
                                    val ny = y + dy
                                    if (nx < 0 || nx >= X || ny < 0 || ny >= Y || field[ny][nx]) {
                                        canPlace = false
                                        break
                                    }
                                }

                                if (canPlace) {
                                    for (block in form) {
                                        val (dx, dy) = block
                                        val nx = x + dx
                                        val ny = y + dy
                                        field[ny][nx] = true
                                    }

                                    countsLeft[index]--
                                    if (place(y, x + 1, countsLeft)) {
                                        return true
                                    }
                                    countsLeft[index]++

                                    for (block in form) {
                                        val (dx, dy) = block
                                        val nx = x + dx
                                        val ny = y + dy
                                        field[ny][nx] = false
                                    }
                                }
                            }
                        }
                    }
                }
                return place(y, x + 1, countsLeft)
            }
            if (place(1, 1, counts.toTypedArray())) 1 else 0
        }
    }

    fun part2(): Any {
        return 0
    }
}


fun main() {
    val testInstance = Day12(true)
    val instance = Day12(false)

    testInstance.part1().let { check(it == EXPECTED_1) { "part1: $it != $EXPECTED_1" } }
    println("part1 ANSWER: ${instance.part1()}")
    testInstance.part2().let {
        check(it == EXPECTED_2) { "part2: $it != $EXPECTED_2" }
        println("part2 ANSWER: ${instance.part2()}")
    }
}