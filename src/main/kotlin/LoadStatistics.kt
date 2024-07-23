class LoadStatistics(private val dictionary: List<Word>) {
    private val dict = Dictionary()
    fun getStatistics(dictionary: List<Word>): Statistics {
        val countWords = this.dictionary.size
        val countLearnedWords = dict.getLearnedWords(this.dictionary).size
        val percentageWordsLearned = countLearnedWords * 100 / countWords
        return Statistics(countLearnedWords, countWords, percentageWordsLearned)
    }
}