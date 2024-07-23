class LearnWordsTrainer(dictionary: List<Word>) {
    private val dict = Dictionary()
    private val newQuestion = NewQuestion(dictionary)

    fun learningWords(dictionary: List<Word>) {
        while (true) {
            val unlearnedWords = dict.getUnlearnedWords(dictionary)
            if (unlearnedWords.isEmpty()) {
                println("Вы выучили все слова!")
                break
            }

            val question = newQuestion.generateQuestion(unlearnedWords)
            val studyWord = question.studyWord
            val wordsForQuestion = question.wordsForQuestion

            while (true) {
                newQuestion.getQuestion(studyWord, wordsForQuestion)
                val answer = readln()

                if (answer.isNotEmpty()) {
                    val indexStudyWord = wordsForQuestion.indexOf(studyWord)
                    val indexAnswerWord = answer.toInt().minus(1)
                    if (indexStudyWord == indexAnswerWord) {
                        studyWord.correctAnswerCount += 1
                        dict.saveDictionary(dictionary)
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
}