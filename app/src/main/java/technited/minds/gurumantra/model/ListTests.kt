package technited.minds.gurumantra.model


import com.google.gson.annotations.SerializedName

data class ListTests(
    @SerializedName("tss")
    val tss: Tss,
    @SerializedName("ts")
    val ts: List<Ts>
)

data class Ts(
    @SerializedName("tId")
    val tId: Int,
    @SerializedName("tsId")
    val tsId: Int,
    @SerializedName("tName")
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
    @SerializedName("description")
    val description: String,
    @SerializedName("total_questions")
    val totalQuestions: Int,
    @SerializedName("start_date")
    val startDate: String,
    @SerializedName("end_date")
    val endDate: String,
    @SerializedName("tImage")
    val tImage: String,
    @SerializedName("created_by")
    val createdBy: Int,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("attempts")
    val attempts: Int,
    @SerializedName("tMetaKey")
    val tMetaKey: String,
    @SerializedName("tMetaDesc")
    val tMetaDesc: String,
    @SerializedName("tStatus")
    val tStatus: Int
)