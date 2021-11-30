package technited.minds.gurumantra.model


import com.google.gson.annotations.SerializedName

data class ListTests(
    @SerializedName("tss", alternate = ["pts"])
    val tss: TestSeriesItem,
    @SerializedName("ts", alternate = ["tts"])
    val ts: List<Ts>?= null
)

data class Ts(
    @SerializedName(value = "tId", alternate = ["pratId", "ptId"])
    val tId: Int,
    @SerializedName(value = "tsId", alternate = ["pratsId", "ptsId"])
    val tsId: Int,
    @SerializedName(value = "tName", alternate = ["pratName", "ptName"])
    val tName: String,
    @SerializedName("ipId")
    val ipId: Int,
    @SerializedName("duration")
    val duration: Int,
    @SerializedName("cost")
    val cost: Int,
    @SerializedName("total_marks")
    val totalMarks: String,
    @SerializedName(value = "description", alternate = ["pratDescription"])
    val description: String,
    @SerializedName("total_questions")
    val totalQuestions: Int,
    @SerializedName("start_date")
    val startDate: String,
    @SerializedName("end_date")
    val endDate: String,
    @SerializedName(value = "tImage", alternate = ["thumbnail","ptsImage"])
    val tImage: String,
    @SerializedName("created_by")
    val createdBy: Int,
    @SerializedName("attempts")
    val attempts: Int,
    @SerializedName(value = "tStatus", alternate = ["pratStatus", "ptStatus"])
    val tStatus: Int,
    @SerializedName("ptQuestions")
    val ptQuestions: String? = null,
    @SerializedName("ptAnswers")
    val ptAnswers: String? = null
)