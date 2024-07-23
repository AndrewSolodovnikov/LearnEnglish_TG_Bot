class NewQuestion(private val dictionary: List<Word>) {
    private val dict = Dictionary()
    fun getQuestion(studyWord: Word, wordsForQuestion: List<Word>) {
        println("Переведи слово: ${studyWord.text}")
        val question = wordsForQuestion.mapIndexed { index, word ->
            "${index + 1} - ${word.translate.trim()}"
        }
        println("Варианты: ${question.joinToString(", ")}, 0 - выйти в меню")
    }

    fun generateQuestion(unlearnedWords: List<Word>): Question {
        var wordsForQuestion = unlearnedWords.shuffled().take(NUMBER_OF_QUESTION)
        val studyWord = wordsForQuestion.random()
        if (wordsForQuestion.size < NUMBER_OF_QUESTION) {
            wordsForQuestion = (wordsForQuestion + dict.getLearnedWords(dictionary).shuffled()
                .take(NUMBER_OF_QUESTION - wordsForQuestion.size)).shuffled()
        }
        return Question(wordsForQuestion, studyWord)
    }
}