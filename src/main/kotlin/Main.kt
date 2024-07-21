const val TARGET_NUMBER_OF_WORDS_LEARNED = 3
const val NUMBER_OF_QUESTION = 4
fun main() {
    val trainer = LearnWordsTrainer()

    while (true) {
        println("Меню: 1 – Учить слова, 2 – Статистика, 0 – Выход")

        when (readln()) {
            "1" -> trainer.learningWords(trainer.dictionary)
            "2" -> {
                val statistics = trainer.statistics(trainer.dictionary)
                println("Выучено ${statistics.countLearnedWords} из " +
                        "${statistics.countWords} | ${statistics.percentageWordsLearned}%")
            }
            "0" -> {
                println("Приложение закрыто!")
                break
            }

            else -> println("Вы ввели неверное значение, попробуйте еще раз!")
        }
    }
}