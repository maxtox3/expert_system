package database

import delimiter
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

object Products : IntIdTable() {
  val name = varchar("name", 255)
  val attributesIds = varchar("attributes_ids_json", 255)
}

object Rules : IntIdTable() {
  val parameterId = integer("parameter_id")
  val attributeId = integer("attribute_id")
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

fun ResultRow.toProduct(): Product {
  val fields = this[Products.attributesIds].split(delimiter)
  return Product(
    id = this[Products.id].value,
    type = fields[0].toInt(),
    frequency = fields[1].toInt(),
    volume = fields[2].toInt(),
    modulesCount = fields[3].toInt(),
    radiator = fields[4].toInt(),
    computerType = fields[5].toInt(),
    gamer = fields[6].toInt(),
    rgb = fields[7].toInt(),
    manufactureCountry = fields[8].toInt(),
    color = fields[9].toInt(),
    name = this[Products.name]
  )
}

fun ResultRow.toRule(): Rule = Rule(
  id = this[Rules.id].value,
  attributeId = this[Rules.attributeId],
  parameterId = this[Rules.parameterId]
)