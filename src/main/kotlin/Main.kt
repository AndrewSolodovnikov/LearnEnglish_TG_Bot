import java.io.File

const val TARGET_NUMBER_OF_WORDS_LEARNED = 3
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

    while (true) {
        println("Меню: 1 – Учить слова, 2 – Статистика, 0 – Выход")

        when (readln()) {
            "1" -> TODO("Учим слова (выход из цикла)")
            "2" -> statistics(dictionary)
            "0" -> {
                println("Приложение закрыто!")
                break
            }

            else -> println("Вы ввели неверное значение, попробуйте еще раз!")
        }
    }
}

fun statistics(dictionary: List<Word>) {
    val countWords = dictionary.size
    val countLearnedWords = dictionary.filter { it.correctAnswerCount >= TARGET_NUMBER_OF_WORDS_LEARNED }.size
    val percentageWordsLearned = countLearnedWords * 100 / countWords
    println("Выучено $countLearnedWords из $countWords | $percentageWordsLearned%")
}

data class Word(
    val text: String,
    val translate: String,
    val correctAnswerCount: Int = 0,
)