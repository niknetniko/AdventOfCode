package common

import java.io.FileNotFoundException
import java.nio.file.Path
import java.nio.file.Paths


/**
 * @author Niko Strijbol
 */
class Runner(private val problem: Problem, private val directory: Path, val fileName: String = "input.txt", private val silent: Boolean = false): Runnable {

    fun inputFilePath(): Path = Paths.get(directory.toString(), fileName).toAbsolutePath()

    override fun run() {
        // Check if the input file exists.
        val filePath = inputFilePath()
        val file = filePath.toFile()
        if (!file.exists()) {
            throw FileNotFoundException()
        }

//        problem.partI(file).takeIf { it !is Unit && !silent }?.let {
//            println("Solution to part I of day ${problem.day} is: ")
//            println("\t$it")
//        }


        problem.partII(file).takeIf { it !is Unit && !silent }?.let {
            println("Solution to part II of day ${problem.day} is: ")
            println("\t$it")
        }
    }
}

val Problem.day: Int
    get() = this::class.simpleName!!.replace(Regex("""[^\d]"""), "").toInt()

fun printErr(errorMsg:String){
    System.err.println(errorMsg)
}

fun run(problem: Problem) {
    val directory = Paths.get("src/day${problem.day}")
    val runner = Runner(problem, directory)
    try {
        runner.run()
    } catch (e: FileNotFoundException) {
        printErr("Could not find input file ${runner.fileName}")
        printErr("Looked in ${runner.inputFilePath()}")
    }

}