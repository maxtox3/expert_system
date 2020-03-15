package model

import kotlinx.serialization.Serializable

@Serializable
data class ResultModel(
  val id: Int,
  val name: String,
  val attributes: List<Answer>,
  val userChosenAttributes: List<Answer>
)