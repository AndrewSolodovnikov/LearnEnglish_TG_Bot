import java.io.File
import kotlin.random.Random

const val TARGET_NUMBER_OF_WORDS_LEARNED = 3
const val NUMBER_OF_QUESTION = 4
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
            "1" -> learningWords(dictionary)
            "2" -> statistics(dictionary)
            "0" -> {
                println("Приложение закрыто!")
                break
            }

            else -> println("Вы ввели неверное значение, попробуйте еще раз!")
        }
    }
}

fun countLearnedWords(dictionary: List<Word>): Int {
    return dictionary.filter { it.correctAnswerCount >= TARGET_NUMBER_OF_WORDS_LEARNED }.size
}

fun countUnlearnedWords(dictionary: List<Word>): Int {
    return dictionary.size - countLearnedWords(dictionary)
}

fun statistics(dictionary: List<Word>) {
    val countWords = dictionary.size
    val countLearnedWords = countLearnedWords(dictionary)
    val percentageWordsLearned = countLearnedWords * 100 / countWords
    println("Выучено $countLearnedWords из $countWords | $percentageWordsLearned%")
}

data class Word(
    val text: String,
    val translate: String,
    val correctAnswerCount: Int = 0,
)
fun learningWords(dictionary: List<Word>) {
    var wordsForQuestion: List<Word>

    while (countUnlearnedWords(dictionary) > 0) {
        if (countUnlearnedWords(dictionary) >= NUMBER_OF_QUESTION) {
            wordsForQuestion = dictionary.filter { it.correctAnswerCount <= TARGET_NUMBER_OF_WORDS_LEARNED }
                .shuffled().take(NUMBER_OF_QUESTION)
        } else {
            wordsForQuestion = dictionary.shuffled().take(NUMBER_OF_QUESTION)
        }

        val studyWord = wordsForQuestion[Random.nextInt(0, NUMBER_OF_QUESTION)]

        println("Переведи слово: ${studyWord.text}")
        println(
            "Варианты: " +
                    "1 - ${wordsForQuestion[0].translate.trim()}, " +
                    "2 - ${wordsForQuestion[1].translate.trim()}, " +
                    "3 - ${wordsForQuestion[2].translate.trim()}, " +
                    "4 - ${wordsForQuestion[3].translate.trim()}"
        )
        val answer = readln()
    }
    println("Вы выучили все слова!")
}