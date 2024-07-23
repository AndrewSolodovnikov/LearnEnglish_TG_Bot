fun main() {
    val dict = Dictionary()
    val trainer = LearnWordsTrainer(dict.dictionary)
    val loadStatistics = LoadStatistics(dict.dictionary)

    while (true) {
        println("Меню: 1 – Учить слова, 2 – Статистика, 0 – Выход")

        when (readln()) {
            "1" -> trainer.learningWords(dict.dictionary)
            "2" -> {
                val statistics = loadStatistics.getStatistics(dict.dictionary)
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