package model

import kotlinx.serialization.Serializable

@Serializable
data class Answer(
    val id: Int,
    val text: String,
    val currentQuestion: Int,
    //поле, указывающее на наличие подвопроса для определения параметра
    val nextQuestion: Int = -1
)