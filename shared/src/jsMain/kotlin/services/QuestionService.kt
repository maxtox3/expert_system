package services

import model.Question
import rpc.Transport
import kotlin.coroutines.CoroutineContext

actual class QuestionService(coroutineContext: CoroutineContext) {
  private val transport = Transport(coroutineContext)

  actual suspend fun getQuestions(): List<Question> {
    return transport.getList("getQuestions", Question.serializer())
  }

  actual suspend fun getQuestion(id: Int): Question {
    return transport.get("getQuestion", Question.serializer(), "id" to id)
  }
}