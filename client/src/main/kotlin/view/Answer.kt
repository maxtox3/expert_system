package view

import com.ccfraser.muirwik.components.MColor
import com.ccfraser.muirwik.components.button.MButtonVariant
import com.ccfraser.muirwik.components.button.mButton
import model.Answer
import react.RBuilder
import styled.DIVBuilder
import styled.styledDiv

fun RBuilder.answerView(answer: Answer, onCLickFunction: () -> Unit, builder: DIVBuilder) {
  styledDiv {
    mButton(caption = answer.text, variant = MButtonVariant.outlined, color = MColor.primary) {
      attrs {
        fullWidth = true
        onClick = { onCLickFunction() }
      }

    }
    builder()
  }

//  styledDiv {
//    attrs.onClickFunction = { onCLick() }
//    styledSpan {
//      css {
//        marginRight = 8.px
//        fontWeight = FontWeight.bold
//      }
//    }
//
//    styledSpan {
//      +answer.text
//    }
//
//
//  }
}