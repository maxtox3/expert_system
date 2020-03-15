import database.*
import io.ktor.application.*
import io.ktor.features.ContentNegotiation
import io.ktor.html.respondHtml
import io.ktor.http.content.resources
import io.ktor.http.content.static
import io.ktor.jackson.jackson
import io.ktor.routing.*
import kotlinx.css.*
import kotlinx.css.properties.lh
import kotlinx.html.*
import model.*
import org.jetbrains.exposed.sql.*
import rpc.rpc
import services.QuestionService

private val globalCss = CSSBuilder().apply {
  body {
    margin(0.px)
    padding(0.px)

    fontSize = 13.px
    fontFamily =
      "-system-ui, -apple-system, BlinkMacSystemFont, Segoe UI, Roboto, Oxygen, Ubuntu, Cantarell, Droid Sans, Helvetica Neue, BlinkMacSystemFont, Segoe UI, Roboto, Oxygen, Ubuntu, Cantarell, Droid Sans, Helvetica Neue, Arial, sans-serif"

    lineHeight = 20.px.lh
  }
}

fun Application.main() {
  install(ContentNegotiation) {
    jackson {}
  }

  initDb()

  routing {
    get("/") {
      call.respondHtml {
        head {
          meta {
            charset = "utf-8"
          }
          title {
            +"Подбор оперативной памяти"
          }
          style {
            unsafe {
              +globalCss.toString()
            }
          }
        }
        body {
          div {
            id = "react-app"
            +"Загрузка..."
          }
          script(src = "/client.js") {
          }
        }
      }
    }

    static("/") {
      resources("/")
    }

    route("/api") {
      rpc(QuestionService::class, Question.serializer())
    }
  }
}

fun initDb() {
  val answers = listOf(
    Answer(1, "PC", 1),
    Answer(2, "Ноутбук", 1),
    Answer(3, "DDR4", 2),
    Answer(4, "DDR3", 2),
    Answer(5, "Не знаю", 2, 3),
    Answer(6, "Более 5 лет назад", 3),
    Answer(7, "Менее 5 лет назад", 3),
    Answer(8, "1", 4),
    Answer(9, "2", 4),
    Answer(10, "4", 4),
    Answer(11, "не знаю", 4, 5),
    Answer(12, "1", 5),
    Answer(13, "2", 5),
    Answer(14, "4", 5),
    Answer(15, "2666", 6),
    Answer(16, "3200", 6),
    Answer(17, "3666", 6),
    Answer(18, "не знаю", 6, 7),
    Answer(19, "приемлемая", 7),
    Answer(20, "средняя", 7),
    Answer(21, "экстримальная", 7),
    Answer(22, "4", 8),
    Answer(23, "8", 8),
    Answer(24, "16", 8),
    Answer(25, "Игровая", 9),
    Answer(26, "Нет", 9, 10),
    Answer(27, "Да", 10),
    Answer(28, "Нет", 10, 11),
    Answer(29, "Да", 11, -1),
    Answer(30, "Нет", 11, -1),
    Answer(31, "Китай", 12),
    Answer(32, "Тайвань", 12),
    Answer(33, "Америка", 12),
    Answer(34, "Не важно", 12),
    Answer(35, "Красный", 13),
    Answer(36, "Черный", 13),
    Answer(37, "Белый", 13),
    Answer(38, "Не важно", 13)
  )

  val questions = listOf(
    Question(1, "Память понадобится для", AnswerType.INT),
    Question(2, "Тип памяти", AnswerType.INT),
    Question(3, "Компьютер (материнская плата) был куплен", AnswerType.INT),
    Question(4, "Количество модулей памяти в комплекте", AnswerType.INT),
    Question(5, "Слотов оперативной памяти в материнской плате", AnswerType.INT),
    Question(6, "Тактовая частота памяти (ГЦ)", AnswerType.INT),
    Question(7, "Скорость работы памяти", AnswerType.INT),
    Question(8, "Объем памяти в одном модуле", AnswerType.INT),
    Question(9, "Игровая", AnswerType.BOOLEAN),
    Question(10, "Необходима RGB подсветка", AnswerType.INT),
    Question(11, "Необходим радиатор охлаждения", AnswerType.INT),
    Question(12, "Страна производитель", AnswerType.INT),
    Question(13, "Цвет", AnswerType.INT)
  )

  database {
    SchemaUtils.create(
      Questions,
      Answers
    )

    val existingQuestions =
      Questions
        .selectAll()
        .map { question ->
          question.toQuestion(
            Answers
              .select { Answers.id eq question[Questions.id] }
              .map { it.toAnswer() })
        }

    val existingAnswers = Answers.selectAll().map { it.toAnswer() }

    questions.forEach { question ->
      val find = existingQuestions.firstOrNull { it.id == question.id }
      if (find == null) {
        Questions.insert { row ->
          row[text] = question.text
          row[ansType] = question.type.value
          row[order] = question.id
        }
      }
    }

    answers.forEach { answer ->
      val find = existingAnswers.firstOrNull { it.id == answer.id }
      if (find == null) {
        Answers.insert { row ->
          row[text] = answer.text
          row[currentQuestion] = answer.currentQuestion
          row[nextQuestion] = answer.nextQuestion
        }
      }
    }
  }
}