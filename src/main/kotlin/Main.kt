import java.io.BufferedReader
import java.io.File
import java.io.FileReader

fun main() {
    val wordsFile: File = File("words.txt")
    wordsFile.createNewFile()
    var reader: BufferedReader? = null

    reader = BufferedReader(FileReader(wordsFile))
    var line: String?

    while (reader.readLine().also { line = it } != null) {
        println(line)
    }
}