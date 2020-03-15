package view

import com.ccfraser.muirwik.components.mTypography
import model.Answer
import react.*

class ResultView : RComponent<ResultProps, ResultState>() {

  init {
    state = ResultState()
  }

  override fun RBuilder.render() {
    mTypography(text = "Ну... это лишь начало. Впереди ждет много злата!")
  }
}

interface ResultProps : RProps {
  var parameters: List<Answer>
  var attributes: List<Answer>
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