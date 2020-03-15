package model

import kotlinx.serialization.Serializable

@Serializable
data class Product(
  val id: Int,
  val type: Int,
  val frequency: Int,
  val volume: Int,
  val modulesCount: Int,
  val radiator: Int,
  val computerType: Int,
  val gamer: Int,
  val rgb: Int,
  val manufactureCountry: Int,
  val color: Int,
  val name: String
)