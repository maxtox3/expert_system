package services

import kotlinx.coroutines.await
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import model.*
import org.w3c.fetch.RequestInit
import kotlin.browser.window
import kotlin.coroutines.CoroutineContext
import kotlin.js.json

class ResultService(private val coroutineContext: CoroutineContext) {

  suspend fun getResult(parameters: List<Answer>, attributes: List<Answer>): ResultModel {
    return withContext(coroutineContext) {
      val response = window.fetch(
        "/api/getResult",
        RequestInit(
          "Post",
          headers = json(
            "Accept" to "application/json",
            "Content-Type" to "application/json"
          ),
          body = Json.stringify(
            ResultRequest.serializer(),
            ResultRequest(attributes = attributes, parameters = parameters)
          ), credentials = "same-origin".asDynamic()
        )
      ).await()

      val resultStringJson = response.text().await()
      Json.parse(ResultModel.serializer(), resultStringJson)
    }
  }
}