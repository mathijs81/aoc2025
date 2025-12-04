private const val EXPECTED_1 = 13
private const val EXPECTED_2 = 43

private class Day04(isTest: Boolean) : Solver(isTest) {
    fun part1(): Any {
        val field = readAsLines()
        val X = field[0].length
        val Y = field.size
        var result = 0
        for (x in 0..<X) {
            for (y in 0..<Y) {
                if (field[y][x] != '@') continue
                var count = 0
                for (dx in -1..1) {
                    for (dy in -1..1) {
                        if (dx == 0 && dy == 0) continue
                        val nx = x + dx
                        val ny = y + dy
                        if (nx in 0..<X && ny in 0..<Y && field[ny][nx] == '@') {
                            count++
                        }
                    }
                }
                if (count < 4) {
                    //println("($x,$y) has $count neighbors")
                    result++
                }
            }
        }
        return result
    }

    fun part2(): Any {
        val field = readAsLines().toMutableList()
        val X = field[0].length
        val Y = field.size
        var result = 0
        while(true) {
            var done = true
            for (x in 0..<X) {
                for (y in 0..<Y) {
                    if (field[y][x] != '@') continue
                    var count = 0
                    for (dx in -1..1) {
                        for (dy in -1..1) {
                            if (dx == 0 && dy == 0) continue
                            val nx = x + dx
                            val ny = y + dy
                            if (nx in 0..<X && ny in 0..<Y && field[ny][nx] == '@') {
                                count++
                            }
                        }
                    }
                    if (count < 4) {
                        //println("($x,$y) has $count neighbors")
                        field[y] = field[y].substring(0, x) + 'O' + field[y].substring(x + 1)
                        result++
                        done = false
                    }
                }
            }
            if (done) break
        }
        return result
    }
}


fun main() {
    val testInstance = Day04(true)
    val instance = Day04(false)

    testInstance.part1().let { check(it == EXPECTED_1) { "part1: $it != $EXPECTED_1" } }
    println("part1 ANSWER: ${instance.part1()}")
    testInstance.part2().let {
        check(it == EXPECTED_2) { "part2: $it != $EXPECTED_2" }
        println("part2 ANSWER: ${instance.part2()}")
    }
}