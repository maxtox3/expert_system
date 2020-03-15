package model

import kotlinx.serialization.Serializable

@Serializable
data class Question(
  val id: Int,
  val text: String,
  val type: AnswerType,
  val answers: List<Answer> = listOf()
)

enum class AnswerType(val value: Int) {
  BOOLEAN(0),
  INT(1),
  STRING(2)
}