import java.io.File

fun main() {
    val wordsFile: File = File("words.txt")
    wordsFile.createNewFile()

    val dictionary = mutableListOf<Word>()

    val lines = wordsFile.readLines()
    lines.forEach {
        val line = it.split("|")
        val correctAnswerCount = line[2].trim().toIntOrNull() ?: 0
        dictionary.add(Word(line[0], line[1], correctAnswerCount))
    }

    println(dictionary)
}

data class Word(
    val text: String,
    val translate: String,
    val correctAnswerCount: Int = 0,
)