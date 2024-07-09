import java.io.File

const val TARGET_NUMBER_OF_WORDS_LEARNED = 3
const val NUMBER_OF_QUESTION = 4
fun main() {
    val dictionary: List<Word> = loadDictionary()

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

private fun loadDictionary() = File("words.txt")
    .readLines()
    .mapNotNull {
        val split = it.split("|")
        if (split.size == 3) {
            val correctAnswerCount = split[2].trim().toIntOrNull() ?: 0
            Word(split[0], split[1], correctAnswerCount)
        } else null
    }

fun getLearnedWords(dictionary: List<Word>): List<Word> {
    return dictionary.filter { it.correctAnswerCount >= TARGET_NUMBER_OF_WORDS_LEARNED }
}

fun getUnlearnedWords(dictionary: List<Word>): List<Word> {
    return dictionary.filter { it.correctAnswerCount <= TARGET_NUMBER_OF_WORDS_LEARNED }
}

fun statistics(dictionary: List<Word>) {
    val countWords = dictionary.size
    val countLearnedWords = getLearnedWords(dictionary).size
    val percentageWordsLearned = countLearnedWords * 100 / countWords
    println("Выучено $countLearnedWords из $countWords | $percentageWordsLearned%")
}

data class Word(
    val text: String,
    val translate: String,
    var correctAnswerCount: Int = 0,
)

fun learningWords(dictionary: List<Word>) {
    while (true) {
        val unlearnedWords = getUnlearnedWords(dictionary)
        if (unlearnedWords.isEmpty()) {
            println("Вы выучили все слова!")
            break
        }

        var wordsForQuestion = unlearnedWords.shuffled().take(NUMBER_OF_QUESTION)
        val studyWord = wordsForQuestion.random()
        if (wordsForQuestion.size < NUMBER_OF_QUESTION) {
            wordsForQuestion = (wordsForQuestion + getLearnedWords(dictionary)
                .shuffled()
                .take(NUMBER_OF_QUESTION - wordsForQuestion.size)).shuffled()
        }

        println("Переведи слово: ${studyWord.text}")
        val question = wordsForQuestion.mapIndexed { index, word ->
            "${index + 1} - ${word.translate.trim()}"
        }
        println("Варианты: ${question.joinToString(", ")}, 0 - выйти в меню")

        val answer = readln()

        val indexStudyWord = wordsForQuestion.indexOf(studyWord)
        val indexAnswerWord = answer.toInt() - 1
        if (indexStudyWord == indexAnswerWord) {
            studyWord.correctAnswerCount += 1
            saveDictionary(dictionary)
        }

        saveDictionary(dictionary)

        if (answer == "0") {
            println("Главное меню")
            return
        }
    }
}

fun saveDictionary(dictionary: List<Word>) {
    File("words.txt").printWriter().use { out ->
        dictionary.forEach { line ->
            out.println("${line.text.trim()} | ${line.translate.trim()} | ${line.correctAnswerCount}")
        }
    }
}