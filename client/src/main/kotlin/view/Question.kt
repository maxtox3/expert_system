package view

import com.ccfraser.muirwik.components.card.*
import com.ccfraser.muirwik.components.spacingUnits
import kotlinx.css.*
import model.Answer
import model.Question
import react.*
import styled.css

class QuestionView : RComponent<QuestionProps, QuestionState>() {

  init {
    state = QuestionState()
  }

  override fun RBuilder.render() {
    val question = props.question
    val answers = props.question.answers

    mCard {
      mCardHeader(title = question.text)
      mCardContent {
        answers.forEach { answer ->
          answerView(answer, onCLickFunction = { props.onAnswerClicked(answer) }) {
            css {
              marginTop = 1.spacingUnits
            }
          }
        }
      }
    }
  }
}

interface QuestionProps : RProps {
  var question: Question
  var onAnswerClicked: (Answer) -> Unit
}

class QuestionState : RState {
  var loading: Boolean = false
}

fun RBuilder.questionView(
  question: Question,
  onAnswerClicked: (Answer) -> Unit,
  handler: RHandler<QuestionProps> = {}
) {
  child(QuestionView::class) {
    attrs.question = question
    attrs.onAnswerClicked = onAnswerClicked
    handler()
  }
}