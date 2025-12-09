import java.util.PriorityQueue

private const val EXPECTED_1 = 40L
private const val EXPECTED_2 = 25272L

private class Day08(isTest: Boolean) : Solver(isTest) {
    val coord = readAsLines().map { it.splitLongs() }
    fun d(i: Int, j: Int): Long {
        val (x1, y1, z1) = coord[i]
        val (x2, y2, z2) = coord[j]
        return (x1-x2)*(x1-x2)+ (y1-y2)*(y1-y2)+    (z1-z2)*(z1-z2)
    }

    fun part1(): Any {
        val conn = if (isTest) 10 else 1000
        val cluster = coord.indices.associateWith { it }.toMutableMap()
        val connections = coord.indices.associateWith { mutableSetOf<Int>() }

        val q = PriorityQueue<Pair<Long, Pair<Int,Int>>>(compareBy { it.first })
        for (i in coord.indices) {
            for (j in i + 1 until coord.size) {
                q.add(d(i,j) to (i to j))
            }
        }

        repeat(conn) {
            val (_, pair) = q.poll()
            val (i, j) = pair
            val newCluster = cluster[j]!!
            val visited = mutableSetOf<Int>()
            fun visit(p: Int) {
                cluster[p] = newCluster
                visited.add(p)
                for (n in connections[p]!!) {
                    if (n !in visited) {
                        visit(n)
                    }
                }
            }
            visit(i)

            connections[i]!!.add(j)
            connections[j]!!.add(i)
        }

        val clusterSizes = cluster.values.groupBy { it }.mapValues { it.value.size }.values.sorted().takeLast(3)
        return clusterSizes[0].toLong() * clusterSizes[1] * clusterSizes[2]
    }
    fun part2(): Any {
        val conn = coord.size - 1
        val cluster = coord.indices.associateWith { it }.toMutableMap()
        val connections = coord.indices.associateWith { mutableSetOf<Int>() }

        val q = PriorityQueue<Pair<Long, Pair<Int,Int>>>(compareBy { it.first })
        for (i in coord.indices) {
            for (j in i + 1 until coord.size) {
                q.add(d(i,j) to (i to j))
            }
        }

        var i = 0
        var j = 0
        repeat(conn) {
            do {
                val (_, pair) = q.poll()
                i = pair.first
                j = pair.second
            } while (cluster[i] == cluster[j])
            val newCluster = cluster[j]!!
            val visited = mutableSetOf<Int>()
            fun visit(p: Int) {
                cluster[p] = newCluster
                visited.add(p)
                for (n in connections[p]!!) {
                    if (n !in visited) {
                        visit(n)
                    }
                }
            }
            visit(i)

            connections[i]!!.add(j)
            connections[j]!!.add(i)
        }
        return coord[i][0] * coord[j][0]
    }

    fun part2_(): Any {
        val conn = coord.size - 1
        val cluster = coord.indices.associateWith { it }.toMutableMap()
        var besti = 0
        var bestj = 0
        repeat(conn) {
            var bestdist = Long.MAX_VALUE
            for (i in coord.indices) {
                for (j in i + 1 until coord.size) if (cluster[i] != cluster[j]) {
                    val dist = d(i,j)
                    if (dist < bestdist ) {
                        bestdist = dist
                        besti = i
                        bestj = j
                    }
                }
            }
            val cluster1 = cluster[besti]!!
            val cluster2 = cluster[bestj]!!
            for (k in cluster.keys) {
                if (cluster[k] == cluster2) {
                    cluster[k] = cluster1
                }
            }
        }
        return coord[besti][0] * coord[bestj][0]
    }
}


fun main() {
    val testInstance = Day08(true)
    val instance = Day08(false)

    testInstance.part1().let { check(it == EXPECTED_1) { "part1: $it != $EXPECTED_1" } }
    println("part1 ANSWER: ${instance.part1()}")
    testInstance.part2().let {
        check(it == EXPECTED_2) { "part2: $it != $EXPECTED_2" }
        println("part2 ANSWER: ${instance.part2()}")
    }
}