import database.*
import model.*
import org.jetbrains.exposed.sql.*

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
    Answer(26, "Нет", 9),
    Answer(27, "Да", 10),
    Answer(28, "Нет", 10),
    Answer(29, "Да", 11),
    Answer(30, "Нет", 11),
    Answer(31, "Китай", 12),
    Answer(32, "Тайвань", 12),
    Answer(33, "Америка", 12),
    Answer(34, "Красный", 13),
    Answer(35, "Черный", 13),
    Answer(36, "Белый", 13),
    Answer(37, "Серый", 13)
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

  val products = listOf(
    Product(
      id = 1,
      type = 3,
      frequency = 17,
      volume = 24,
      modulesCount = 9,
      radiator = 29,
      computerType = 1,
      gamer = 25,
      rgb = 27,
      manufactureCountry = 32,
      color = 36,
      name = "G.SKILL TridentZ Neo"
    ),
    Product(
      id = 2,
      type = 3,
      frequency = 15,
      volume = 23,
      modulesCount = 9,
      radiator = 30,
      computerType = 1,
      gamer = 25,
      rgb = 27,
      manufactureCountry = 32,
      color = 36,
      name = "HyperX Fury Black"
    ),
    Product(
      id = 3,
      type = 4,
      frequency = 15,
      volume = 22,
      modulesCount = 8,
      radiator = 30,
      computerType = 2,
      gamer = 26,
      rgb = 28,
      manufactureCountry = 31,
      color = 35,
      name = "Kingston NoteBook"
    ),
    Product(
      id = 4,
      type = 3,
      frequency = 16,
      volume = 23,
      modulesCount = 10,
      radiator = 29,
      computerType = 1,
      gamer = 25,
      rgb = 28,
      manufactureCountry = 31,
      color = 34,
      name = "Viper bloody red"
    ),
    Product(
      id = 5,
      type = 3,
      frequency = 17,
      volume = 24,
      modulesCount = 10,
      radiator = 29,
      computerType = 1,
      gamer = 25,
      rgb = 27,
      manufactureCountry = 33,
      color = 36,
      name = "Corsair white"
    ),
    Product(
      id = 6,
      type = 4,
      frequency = 16,
      volume = 22,
      modulesCount = 10,
      radiator = 30,
      computerType = 1,
      gamer = 26,
      rgb = 28,
      manufactureCountry = 32,
      color = 37,
      name = "G.Skill TridentZ Gray"
    ),
    Product(
      id = 7,
      type = 3,
      frequency = 16,
      volume = 23,
      modulesCount = 8,
      radiator = 30,
      computerType = 2,
      gamer = 26,
      rgb = 28,
      manufactureCountry = 32,
      color = 37,
      name = "Crucial NoteBook"
    ),
    Product(
      id = 8,
      type = 4,
      frequency = 15,
      volume = 22,
      modulesCount = 9,
      radiator = 29,
      computerType = 1,
      gamer = 25,
      rgb = 28,
      manufactureCountry = 33,
      color = 36,
      name = "Ballistix White"
    ),
    Product(
      id = 9,
      type = 3,
      frequency = 15,
      volume = 23,
      modulesCount = 9,
      radiator = 30,
      computerType = 1,
      gamer = 26,
      rgb = 28,
      manufactureCountry = 31,
      color = 37,
      name = "Viper Gray"
    ),
    Product(
      id = 10,
      type = 3,
      frequency = 17,
      volume = 24,
      modulesCount = 9,
      radiator = 29,
      computerType = 1,
      gamer = 25,
      rgb = 27,
      manufactureCountry = 33,
      color = 37,
      name = "Ballistix Gray RGB"
    )
  )

  val rules = listOf(
    Rule(
      id = 1,
      parameterId = 6,
      attributeId = 4
    ),
    Rule(
      id = 2,
      parameterId = 7,
      attributeId = 3
    ),
    Rule(
      id = 3,
      parameterId = 12,
      attributeId = 8
    ),
    Rule(
      id = 4,
      parameterId = 13,
      attributeId = 9
    ),
    Rule(
      id = 5,
      parameterId = 14,
      attributeId = 10
    ),
    Rule(
      id = 6,
      parameterId = 19,
      attributeId = 15
    ),
    Rule(
      id = 7,
      parameterId = 20,
      attributeId = 16
    ),
    Rule(
      id = 8,
      parameterId = 21,
      attributeId = 17
    )
  )

  database {
    SchemaUtils.create(
      Questions,
      Answers,
      Products,
      Rules
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
    val existingProduct = Products.selectAll().map { it.toProduct() }
    val existingRules = Rules.selectAll().map { it.toRule() }

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

    products.forEach { product ->
      val find = existingProduct.firstOrNull { it.id == product.id }
      if (find == null) {
        Products.insert { row ->
          row[name] = product.name
          row[attributesIds] = "${product.type},${product.frequency},${product.volume},${product.modulesCount},${product.radiator},${product.computerType},${product.gamer},${product.rgb},${product.manufactureCountry},${product.color}"
        }
      }
    }

    rules.forEach { rule ->
      val find = existingRules.firstOrNull { it.id == rule.id }
      if (find == null) {
        Rules.insert { row ->
          row[attributeId] = rule.attributeId
          row[parameterId] = rule.parameterId
        }
      }
    }
  }
}