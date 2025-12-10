import com.google.ortools.Loader
import com.google.ortools.linearsolver.MPSolver
import java.util.*

private const val EXPECTED_1 = 7
private const val EXPECTED_2 = 33

private class Day10(isTest: Boolean) : Solver(isTest) {
    fun part1(): Any {
        return readAsLines().sumOf { line ->
            val parts = line.split(" ")
            val targetStr = parts[0].removePrefix("[").removeSuffix("]")
            var targetInt = 0
            for (c in targetStr.indices) {
                if (targetStr[c] == '#') {
                    targetInt = targetInt or (1.shl(c))
                }
            }
            val buttons = parts.dropLast(1).drop(1).map {
                var mask = 0
                for (c in it.splitInts()) {
                    mask = mask or (1.shl(c))
                }
                mask
            }
            var countReachable = Array<Int>(1.shl(targetStr.length)) { -1 }

            val pqueue = PriorityQueue<Int>(compareBy { countReachable[it] })
            countReachable[0] = 0
            pqueue.add(0)

            while (pqueue.isNotEmpty()) {
                val pos = pqueue.poll()
                val c = countReachable[pos]
                for (b in buttons) {
                    val newpos = pos xor b
                    if (countReachable[newpos] == -1 || countReachable[newpos]!! > c + 1) {
                        countReachable[newpos] = c + 1
                        pqueue.add(newpos)
                    }
                }
            }

            countReachable[targetInt]!!
        }
    }
    
    // Using external solver because couldn't get my own branch-and-bound to work fast enough
    fun part2(): Any {
        Loader.loadNativeLibraries();
        return readAsLines().sortedByDescending { it.length }.sumOf { line ->
            val parts = line.split(" ")
            val targetCounts = parts.last().splitInts()
            val N = targetCounts.size
            val buttons = parts.dropLast(1).drop(1).map {
                it.splitInts().sorted()
            }

            val solver = MPSolver.createSolver("CBC")
            val vars = buttons.map {
                val v = solver.makeIntVar(0.0, targetCounts.max().toDouble(), "")
                solver.objective().setCoefficient(v, 1.0)
                v
            }

            for (i in 0..<N) {
                val constraint = solver.makeConstraint(targetCounts[i].toDouble(), targetCounts[i].toDouble())
                for (j in buttons.indices) {
                    if (i in buttons[j]) {
                        constraint.setCoefficient(vars[j], 1.0)
                    }
                }
            }

            solver.objective().minimization()
            solver.solve()

            vars.sumOf { it.solutionValue().toInt() }
        }
    }

    /*
    Custom optimization mess:

        fun readBestStore(): Map<Int, Int> {
        return File("beststore").let {
            if (!it.exists()) {
                mapOf()
            } else
                it.readLines().map {
                    it.splitInts()
                }.associate { it[0] to it[1] }
        }
    }

    fun writeBestStore(map: Map<Int, Int>) {
        File("beststore").writeText(
            map.entries.joinToString("\n") { "${it.key} ${it.value}" }
        )
    }

            return readAsLines().sortedByDescending { it.length }.sumOf { line ->
            val hash = line.hashCode()
            if (bestStore.containsKey(hash)) {
                return@sumOf bestStore[hash]!!
            }
            val parts = line.split(" ")
            val targetCounts = parts.last().splitInts()
            val N = targetCounts.size
            val buttons = parts.dropLast(1).drop(1).map {
                it.splitInts().sorted()
            }



            fun addButtons(currentCounts: List<Int>, buttonIndex: Int, left: Int): Int {
                if (buttonIndex >= buttons.size) {
                    return if (currentCounts.all { it == 0 }) 0 else 100000
                }
                val button = buttons[buttonIndex]
                if (button[0] > 0) {
                    for (i in 0 until button[0]) {
                        if (currentCounts[i] != 0) {
                            return 100000
                        }
                    }
                }

                var maxPresses = left
                for (i in button) {
                    maxPresses = minOf(
                        maxPresses,
                        currentCounts[i]
                    )
                }

                var best = left
                fun tryPress(presses: Int) {
                    val newCounts = currentCounts.toMutableList()
                    for (b in button) {
                        newCounts[b] -= presses
                    }
                    val result = addButtons(newCounts, buttonIndex + 1, best - presses)
                    best = minOf(best, result + presses)
                }
                for (presses in 0..minOf(5, maxPresses)) {
                    tryPress(presses)
                }
                for (presses in maxPresses downTo max(6, maxPresses - 5)) {
                    tryPress(presses)
                }

                return best
            }

            addButtons(targetCounts, 0, targetCounts.sum() + 1).also {
                if (it <= targetCounts.sum()) {
                    bestStore[hash] = it
                    writeBestStore(bestStore)
                } else {
                    println("Could not solve line: $line")
                }
                println("Line result: $it")
            }
        }

     */
}


fun main() {
    val testInstance = Day10(true)
    val instance = Day10(false)

    testInstance.part1().let { check(it == EXPECTED_1) { "part1: $it != $EXPECTED_1" } }
    println("part1 ANSWER: ${instance.part1()}")
    testInstance.part2().let {
        check(it == EXPECTED_2) { "part2: $it != $EXPECTED_2" }
        println("part2 ANSWER: ${instance.part2()}")
    }
}