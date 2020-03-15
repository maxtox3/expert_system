package services

import model.Question

expect class QuestionService {
  suspend fun getQuestions(): List<Question>
  suspend fun getQuestion(id: Int): Question
}