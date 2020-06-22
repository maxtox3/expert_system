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
import model.Question
import rpc.rpc
import services.QuestionService
import services.getResult

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
      post("/getResult") {
        getResult(call)
      }
    }
  }
}