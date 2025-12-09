import kotlin.math.absoluteValue

private const val EXPECTED_1 = 50L
private const val EXPECTED_2 = 24L

private class Day09(isTest: Boolean) : Solver(isTest) {
    fun part1(): Any {
        val coords = readAsLines().map { it.splitLongs().let { it[0] to it[1] } }
        println(coords.size)
        var max = 0L

        for (c in coords)
            for (d in coords) {
                max = maxOf(
                    max,
                    (c.first - d.first + 1).absoluteValue * (c.second - d.second + 1).absoluteValue
                )
            }

        return max
    }

    fun pointInPoly(point: Pair<Long, Long>, polygon: List<Pair<Long, Long>>): Boolean {
        var intersections = 0
        for (i in polygon.indices) {
            val prev = polygon[(i - 1 + polygon.size) % polygon.size]
            val a = polygon[i]
            val b = polygon[(i + 1) % polygon.size]
            val next = polygon[(i + 2) % polygon.size]

            // On edge that a,b defines?
            if ((a.first == b.first)) {
                check(prev.second == a.second && b.second == next.second)
                if (point.second in minOf(a.second, b.second)..maxOf(
                        a.second,
                        b.second
                    ) && point.first == a.first
                ) {
                    return true
                }
                if (point.first < a.first && point.second in minOf(
                        a.second,
                        b.second
                    )..<maxOf(a.second, b.second)
                ) {
                    intersections++
                }
            } else {
                check(a.second == b.second)
                if (a.second == point.second && point.first in minOf(
                        a.first,
                        b.first
                    )..maxOf(a.first, b.first)
                ) {
                    return true
                }
            }
        }
        return intersections % 2 == 1
    }

    fun part2(): Any {
        val coords = readAsLines().map { it.splitLongs().let { it[0] to it[1] } }
        val pointsOutside = mutableSetOf<Pair<Long, Long>>()
        val xs = coords.map { it.first }.toSet()
        val ys = coords.map { it.second }.toSet()

        for (candidate in coords) {
            for ((dx, dy) in listOf(-1L to 0L, 1L to 0L, 0L to -1L, 0L to 1L)) {
                if (!pointInPoly((candidate.first + dx) to (candidate.second + dy), coords))
                    pointsOutside.add(candidate.first + dx to candidate.second + dy)
            }
        }

        var max = 0L

        for (c in coords)
            for (d in coords) {
                val ar =
                    ((c.first - d.first).absoluteValue + 1) * ((c.second - d.second).absoluteValue + 1)
                if (ar < max) {
                    continue
                }

                val x1 = minOf(c.first, d.first)
                val x2 = maxOf(c.first, d.first)
                val y1 = minOf(c.second, d.second)
                val y2 = maxOf(c.second, d.second)
                var ok = true
                for (p in pointsOutside) {
                    if (p.first >= x1 && p.first <= x2 && p.second >= y1 && p.second <= y2) {
                        if (ar == 1439894345L) {
                            println("$c, $d, excluded by $p")
                        }
                        ok = false
                        break
                    }
                }
                if (ok)
                    outer@ for (x in xs) {
                        if (x >= x1 && x <= x2) {
                            for (y in ys) {
                                if (y >= y1 && y <= y2) {
                                    if (!pointInPoly(x to y, coords)) {
                                        ok = false
                                        break@outer
                                    }
                                }
                            }
                        }
                    }
                if (ok)
                    max = ar
            }
        return max
    }
}

fun main() {
    val testInstance = Day09(true)
    val instance = Day09(false)

    testInstance.part1().let { check(it == EXPECTED_1) { "part1: $it != $EXPECTED_1" } }
    println("part1 ANSWER: ${instance.part1()}")
    testInstance.part2().let {
        check(it == EXPECTED_2) { "part2: $it != $EXPECTED_2" }
        println("part2 ANSWER: ${instance.part2()}")
    }
}