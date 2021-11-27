package technited.minds.gurumantra.model


import com.google.gson.annotations.SerializedName

data class ListTests(
    @SerializedName("tss")
    val tss: TestSeriesItem,
    @SerializedName("ts")
    val ts: List<Ts>
)

data class Ts(
    @SerializedName(value = "tId", alternate = ["pratId"])
    val tId: Int,
    @SerializedName(value = "tsId", alternate = ["pratsId"])
    val tsId: Int,
    @SerializedName(value = "tName", alternate = ["pratName"])
    val tName: String,
    @SerializedName("ipId")
    val ipId: Int,
    @SerializedName("duration")
    val duration: Int,
    @SerializedName("cost")
    val cost: Any,
    @SerializedName("validity")
    val validity: Any,
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
    @SerializedName(value = "tImage", alternate = ["thumbnail"])
    val tImage: String,
    @SerializedName("created_by")
    val createdBy: Int,
    @SerializedName("attempts")
    val attempts: Int,
    @SerializedName(value = "tStatus", alternate = ["pratStatus"])
    val tStatus: Int
)