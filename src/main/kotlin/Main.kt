import java.io.File

fun main() {
    val wordsFile: File = File("words.txt")
    wordsFile.createNewFile()

    val lines = wordsFile.readLines()
    lines.forEach { println(it) }
}