package services

import database.*
import delimiter
import io.ktor.application.ApplicationCall
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import model.*
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll


suspend fun getResult(call: ApplicationCall) {
  val request = call.receive<ResultRequest>()

  try {
    val userChosenAnswers = request.attributes
    val parameters = request.parameters

    val attributes = calculateAttributes(parameters) + userChosenAnswers
    val product = calculateResult(attributes, userChosenAnswers)
    call.respond(product)
  } catch (e: Throwable) {
    println(e.toString())
    call.respond(HttpStatusCode.NotFound)
  }
}

internal fun calculateAttributes(parameters: List<Answer>): List<Answer> =
  parameters.map { param ->
    database {
      Rules
        .select { Rules.parameterId eq param.id }
        .first()
        .toRule()
        .let { rule ->
          Answers.select { Answers.id eq rule.attributeId }.first().toAnswer()
        }
    }
  }

internal fun calculateResult(
  calculatedAttributes: List<Answer>,
  userChosenAnswers: List<Answer>
): ResultModel {
  return database {
    val products = Products.selectAll()
    var countOfSameAttributes = 0
    var lastProduct: Pair<Int, Int>? = null
    products.forEach {
      val productAttributes = it[Products.attributesIds].split(delimiter).map { id -> id.toInt() }
      productAttributes.map { productAttribute ->
        calculatedAttributes.forEach { attribute ->
          if (attribute.id == productAttribute) {
            countOfSameAttributes += 1
          }
        }
      }
      if (lastProduct == null) {
        lastProduct = it[Products.id].value to 0
      }
      if (lastProduct!!.second < countOfSameAttributes) {
        lastProduct = it[Products.id].value to countOfSameAttributes
      }
      countOfSameAttributes = 0
    }
    Products.select { Products.id eq lastProduct!!.first }.first().let {
      ResultModel(
        id = it[Products.id].value,
        name = it[Products.name],
        attributes = it[Products.attributesIds]
          .split(delimiter)
          .map { stringId ->
            Answers.select { Answers.id eq stringId.toInt() }.first().toAnswer()
          },
        userChosenAttributes = userChosenAnswers
      )
    }
  }
}