package view

import com.ccfraser.muirwik.components.*
import com.ccfraser.muirwik.components.card.*
import kotlinx.css.height
import kotlinx.css.px
import react.*
import services.ResultViewModel
import styled.css

class ResultView : RComponent<ResultProps, ResultState>() {

  init {
    state = ResultState()
  }

  override fun RBuilder.render() {

    mCard {
      mCardActionArea {
        mCardMedia(image = "/${props.result.id}.jpg") {
          css {
            height = 140.px
          }
        }
        mCardContent {
          mTypography(
            text = "Наиболее подходящий варинат: ${props.result.name}",
            variant = MTypographyVariant.h5,
            component = "h2"
          )
          props.result.specification.forEach {
            mTypography(
              text = "${it.questionText}:  ${it.answerText}",
              variant = MTypographyVariant.body2,
              color = MTypographyColor.textSecondary,
              component = "p"
            )
          }
        }
      }
    }
  }
}

interface ResultProps : RProps {
  var result: ResultViewModel
}

class ResultState : RState {
  var loading: Boolean = false
}

fun RBuilder.resultView(
  result: ResultViewModel,
  handler: RHandler<ResultProps> = {}
) {
  child(ResultView::class) {
    attrs.result = result
    handler()
  }
}