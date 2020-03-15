package database

import model.*
import org.jetbrains.exposed.dao.IntIdTable
import org.jetbrains.exposed.sql.ResultRow

object Questions : IntIdTable() {
  val text = varchar("text", 255)
  val ansType = integer("answer_type").default(AnswerType.BOOLEAN.value)
  val order = integer("order")
}

object Answers : IntIdTable() {
  val text = varchar("text", 255)
  val currentQuestion = integer("question_id")
  val nextQuestion = integer("next_question_id").default(-1)
}

fun ResultRow.toQuestion(answers: List<Answer>): Question = Question(
  id = this[Questions.id].value,
  type = when (this[Questions.ansType]) {
    1 -> AnswerType.BOOLEAN
    2 -> AnswerType.INT
    else -> AnswerType.STRING
  },
  text = this[Questions.text],
  answers = answers
)

fun ResultRow.toAnswer(): Answer = Answer(
  id = this[Answers.id].value,
  currentQuestion = this[Answers.currentQuestion],
  nextQuestion = this[Answers.nextQuestion],
  text = this[Answers.text]
)