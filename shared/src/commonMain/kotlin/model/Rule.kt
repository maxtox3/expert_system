package model

import kotlinx.serialization.Serializable

@Serializable
data class Rule(
  val id: Int,
  val attributeId: Int,
  val parameterId: Int
)