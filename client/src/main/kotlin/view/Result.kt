package view

import com.ccfraser.muirwik.components.mTypography
import model.Answer
import react.*

class ResultView : RComponent<ResultProps, ResultState>() {

//  lateinit var resultService: ResultService

  init {
    state = ResultState()
  }

  override fun componentDidMount() {

//    resultService = ResultService(
//      coroutineContext = props.coroutineScope.coroutineContext,
//      parameters = props.parameters,
//      attributes = props.attributes
//    )
//    props.coroutineScope.launch {
//      val questions = questionsService.getQuestions()
//
//      setState {
//        questionsWithAnswers = questions
//        currentQuestion = questions.first()
//      }
//    }
  }

  override fun RBuilder.render() {
    mTypography(text = "Hello there, general Kenoby")
//    val question = props.question
//    val answers = props.question.answers
//
//    mCard {
//      mCardHeader(title = question.text)
//      mCardContent {
//        answers.forEach { answer ->
//          answerView(answer, onCLickFunction = { props.onAnswerClicked(answer) }) {
//            css {
//              marginTop = 1.spacingUnits
//            }
//          }
//        }
//      }
//    }
  }
}

interface ResultProps : RProps {
  var parameters: List<Answer>
  var attributes: List<Answer>
  var onAnswerClicked: (Answer) -> Unit
}

class ResultState : RState {
  var loading: Boolean = false
}

fun RBuilder.resultView(
  parameters: List<Answer>,
  attributes: List<Answer>,
  handler: RHandler<ResultProps> = {}
) {
  child(ResultView::class) {
    attrs.attributes = attributes
    attrs.parameters = parameters
    handler()
  }
}