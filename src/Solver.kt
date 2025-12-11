import java.io.File

open class Solver(val isTest: Boolean, val extraPath: String = "") {
    private val filename
        get() = "${this.javaClass.simpleName.let { it.substringBefore('_', it) }}${if (isTest) "_test" else ""}.txt"

    open fun inputFile() = File("src$extraPath", filename)

    fun readAsLines() = inputFile().readLines()
    fun readAsString() = inputFile().readText()
}