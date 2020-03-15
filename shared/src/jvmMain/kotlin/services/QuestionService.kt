package services

import database.*
import model.Question
import org.jetbrains.exposed.sql.*
import rpc.RPCService

actual class QuestionService : RPCService {

  actual suspend fun getQuestions(): List<Question> {
    return database {
      Questions
        .selectAll()
        .map {
          getAnswersForQuestion(it)
        }
    }
  }

  actual suspend fun getQuestion(id: Int): Question {
    return database {
      Questions.select { Questions.id eq id }.firstOrNull()?.let {
        getAnswersForQuestion(it)
      }
    } ?: throw IllegalArgumentException("no such question: $id")
  }

  private fun getAnswersForQuestion(it: ResultRow): Question {
    val answersForQuestion = Answers.select { Answers.currentQuestion eq it[Questions.id].value }.map { resultRow -> resultRow.toAnswer() }
    return it.toQuestion(answersForQuestion)
  }

}