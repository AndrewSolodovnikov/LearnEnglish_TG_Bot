import java.io.File

class Dictionary {
    var dictionary: List<Word> = loadDictionary()

    private fun loadDictionary() = File("words.txt").readLines().mapNotNull {
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

    fun saveDictionary(updateDictionary: List<Word>) {
        File("words.txt").printWriter().use { out ->
            updateDictionary.forEach { line ->
                out.println("${line.text.trim()} | ${line.translate.trim()} | ${line.correctAnswerCount}")
            }
        }
    }
}