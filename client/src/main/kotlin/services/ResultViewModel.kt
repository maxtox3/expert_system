package services

data class ResultViewModel(
  val id: Int,
  val name: String,
  val questionsAnswers: List<QuestionAnswer>,
  val specification: List<QuestionAnswer>
)

data class QuestionAnswer(
  val questionText: String,
  val answerText: String
)