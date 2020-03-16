package view

import com.ccfraser.muirwik.components.*
import com.ccfraser.muirwik.components.styles.ThemeOptions
import com.ccfraser.muirwik.components.styles.createMuiTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.css.padding
import kotlinx.css.px
import model.*
import react.*
import services.*
import styled.*

//val jetbrainsLogo = kotlinext.js.require("@jetbrains/logos/jetbrains/jetbrains-simple.svg")

private object ApplicationStyles : StyleSheet("ApplicationStyles", isStatic = true) {
  val wrapper by css {
    padding(32.px, 16.px)
  }
}

class ApplicationComponent : RComponent<ApplicationProps, ApplicationState>() {

  private lateinit var questionsService: QuestionService
  private lateinit var resultService: ResultService

  private var themeColor = "light"

  init {
    state = ApplicationState()
  }

  override fun componentDidMount() {
    questionsService = QuestionService(props.coroutineScope.coroutineContext)
    resultService = ResultService(props.coroutineScope.coroutineContext)

    props.coroutineScope.launch {
      val questions = questionsService.getQuestions()

      setState {
        questionsWithAnswers = questions
        currentQuestion = questions.first()
      }
    }
  }

  override fun RBuilder.render() {
    console.log("attributes: ${state.attributes.size}")
    console.log("parameters: ${state.parameters.size}")

    val themeOptions: ThemeOptions = js("({palette: { type: 'placeholder', primary: {main: 'placeholder'}}})")
    themeOptions.palette?.type = themeColor
    themeOptions.palette?.primary.main = Colors.Blue.shade500.toString()

    mThemeProvider(createMuiTheme(themeOptions)) {
      mCssBaseline()
      mContainer {
        attrs {
          component = "main"
          maxWidth = "xs"
        }

        styledDiv {
          css {
            +ApplicationStyles.wrapper
          }

          if (state.result == null) {
            state.currentQuestion?.let {
              styledDiv {
                questionView(question = it, onAnswerClicked = { answer -> onAnswerClicked(answer) })
              }
            }
          } else {
            state.result?.let {
              styledDiv {
                resultView(it)
              }
            }
          }
        }

      }
    }
  }

  private fun onAnswerClicked(answer: Answer) {
    val answerHasNextQuestion = answer.nextQuestion != -1
    val mCurrentQuestion = state.currentQuestion
    val currentQuestionID = mCurrentQuestion?.id
    val currentQuestionHasAnswerWithSubQuestion = mCurrentQuestion?.answers?.firstOrNull { it.nextQuestion != -1 } != null
    //проверяем: есть ли подвопрос?
    if (answerHasNextQuestion) {
      //есть подвопрос, показываем его
      showSubQuestion(answer.nextQuestion)
    } else {
      //подвопроса нет. Проверяем, является ли ответ параметром
      if (state.currentQuestionIsSubQuestion.not()) {
        //ответ является аттрибутом, добавляем аттрибут в список атрибутов
        addAttribute(answer)
      } else {
        //ответ является параметром, добавляем параметр в список параметров
        addParameter(answer)
      }

      if (currentQuestionID != null) {
        val nextQuestionId = if (currentQuestionHasAnswerWithSubQuestion) {
          currentQuestionID + 2
        } else {
          currentQuestionID + 1
        }

        val nextQuestion = state.questionsWithAnswers.firstOrNull { it.id == nextQuestionId }
        //проверяем, есть ли следующий вопрос
        if (nextQuestion != null) {
          //следующий вопрос есть, продолжаем опрос
          showNextQuestion(nextQuestion)
        } else {
          //следующего вопроса нет, вычисляем результат
          calculateResult()
        }
      }
    }
  }

  private fun showSubQuestion(nextQuestionId: Int) {
    setState {
      currentQuestion = questionsWithAnswers.firstOrNull { it.id == nextQuestionId }
      currentQuestionIsSubQuestion = true
    }
  }

  private fun showNextQuestion(nextQuestion: Question?) {
    setState {
      currentQuestion = nextQuestion
      currentQuestionIsSubQuestion = false
    }
  }

  private fun addAttribute(answer: Answer) {
    val attrs = state.attributes
    attrs.add(answer)
    setState {
      attributes = attrs
    }
  }

  private fun addParameter(answer: Answer) {
    val params = state.parameters
    params.add(answer)

    setState {
      parameters = params
    }
  }

  private fun calculateResult() {
    props.coroutineScope.launch {
      val resultModel = resultService.getResult(state.parameters, state.attributes)
      val resultViewModel = ResultModelToResultViewModelMapper.invoke(
        resultModel,
        state.questionsWithAnswers
      )
      setState {
        result = resultViewModel
      }
    }
  }
}

interface ApplicationProps : RProps {
  var coroutineScope: CoroutineScope
}

class ApplicationState : RState {
  var questionsWithAnswers: List<Question> = emptyList()
  var attributes: MutableList<Answer> = mutableListOf()
  var parameters: MutableList<Answer> = mutableListOf()
  var currentQuestion: Question? = null
  var result: ResultViewModel? = null
  var currentQuestionIsSubQuestion: Boolean = false
}
