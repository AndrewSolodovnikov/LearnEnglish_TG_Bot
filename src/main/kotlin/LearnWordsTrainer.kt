import java.io.File

data class Word(
    val text: String,
    val translate: String,
    var correctAnswerCount: Int = 0,
)

data class Statistics(
    val countLearnedWords: Int,
    val countWords: Int,
    val percentageWordsLearned: Int,
)

data class Question(
    val wordsForQuestion: List<Word>,
    val studyWord: Word,
)

class LearnWordsTrainer {
    val dictionary: List<Word> = loadDictionary()

    private fun loadDictionary() = File("words.txt").readLines().mapNotNull {
            val split = it.split("|")
            if (split.size == 3) {
                val correctAnswerCount = split[2].trim().toIntOrNull() ?: 0
                Word(split[0], split[1], correctAnswerCount)
            } else null
        }

    private fun getLearnedWords(dictionary: List<Word>): List<Word> {
        return dictionary.filter { it.correctAnswerCount >= TARGET_NUMBER_OF_WORDS_LEARNED }
    }

    private fun getUnlearnedWords(dictionary: List<Word>): List<Word> {
        return dictionary.filter { it.correctAnswerCount <= TARGET_NUMBER_OF_WORDS_LEARNED }
    }

    fun learningWords(dictionary: List<Word>) {
        while (true) {
            val unlearnedWords = getUnlearnedWords(dictionary)
            if (unlearnedWords.isEmpty()) {
                println("Вы выучили все слова!")
                break
            }

            val question = generateQuestion(unlearnedWords)
            val studyWord = question.studyWord
            val wordsForQuestion = question.wordsForQuestion

            while (true) {
                getQuestion(studyWord, wordsForQuestion)
                val answer = readln()

                if (answer.isNotEmpty()) {
                    val indexStudyWord = wordsForQuestion.indexOf(studyWord)
                    val indexAnswerWord = answer.toInt().minus(1)
                    if (indexStudyWord == indexAnswerWord) {
                        studyWord.correctAnswerCount += 1
                        saveDictionary(dictionary)
                        break
                    }
                }

                if (answer == "0") {
                    println("Главное меню")
                    return
                }
            }
        }
    }

    fun statistics(dictionary: List<Word>): Statistics {
        val countWords = this.dictionary.size
        val countLearnedWords = getLearnedWords(this.dictionary).size
        val percentageWordsLearned = countLearnedWords * 100 / countWords
        return Statistics(countLearnedWords, countWords, percentageWordsLearned)
    }

    private fun saveDictionary(dictionary: List<Word>) {
        File("words.txt").printWriter().use { out ->
            dictionary.forEach { line ->
                out.println("${line.text.trim()} | ${line.translate.trim()} | ${line.correctAnswerCount}")
            }
        }
    }

    private fun getQuestion(studyWord: Word, wordsForQuestion: List<Word>) {
        println("Переведи слово: ${studyWord.text}")
        val question = wordsForQuestion.mapIndexed { index, word ->
            "${index + 1} - ${word.translate.trim()}"
        }
        println("Варианты: ${question.joinToString(", ")}, 0 - выйти в меню")
    }

    private fun generateQuestion(unlearnedWords: List<Word>): Question {
        var wordsForQuestion = unlearnedWords.shuffled().take(NUMBER_OF_QUESTION)
        val studyWord = wordsForQuestion.random()
        if (wordsForQuestion.size < NUMBER_OF_QUESTION) {
            wordsForQuestion = (wordsForQuestion + getLearnedWords(dictionary).shuffled()
                .take(NUMBER_OF_QUESTION - wordsForQuestion.size)).shuffled()
        }
        return Question(wordsForQuestion, studyWord)
    }
}