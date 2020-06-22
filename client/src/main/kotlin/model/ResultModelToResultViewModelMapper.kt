package model

import services.QuestionAnswer
import services.ResultViewModel

object ResultModelToResultViewModelMapper {

  fun invoke(resultModel: ResultModel, questions: List<Question>): ResultViewModel {
    val questionAnswers =
      resultModel
        .userChosenAttributes
        .map { userAnswer ->
          QuestionAnswer(
            questionText = questions.first { it.id == userAnswer.currentQuestion }.text,
            answerText = userAnswer.text
          )
        }

    val specifications =
      resultModel
        .attributes
        .map { attribute ->
          QuestionAnswer(
            questionText = questions.first { it.id == attribute.currentQuestion }.text,
            answerText = attribute.text
          )
        }
    return ResultViewModel(
      id = resultModel.id,
      name = resultModel.name,
      questionsAnswers = questionAnswers,
      specification = specifications
    )
  }
}