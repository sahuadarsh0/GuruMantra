package technited.minds.gurumantra.model


import com.google.gson.annotations.SerializedName


data class EndTest(

    @SerializedName("tsd")
    val tsd: Tsd,
    @SerializedName("ques")
    val ques: List<AnswersItem>
)

data class Tsd(

    @SerializedName("testId")
    val testId: Int,
    @SerializedName("userId")
    val userId: Int,
    @SerializedName("timeTaken")
    val timeTaken: Int,
)

data class Result(
    @SerializedName("resulturl")
    val resultUrl: String
)
