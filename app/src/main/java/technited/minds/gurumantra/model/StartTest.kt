package technited.minds.gurumantra.model


import com.google.gson.annotations.SerializedName

data class StartTest(
    @SerializedName("tqns")
    val tqns: Int,
    @SerializedName("resId")
    val resId: Int,
    @SerializedName("tsts")
    val tsts: Tsts,
    @SerializedName("tsecs")
    val tsecs: List<Tsec>,
    @SerializedName("ques")
    val ques: List<QuestionsItem>
)

data class Tsts(
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
    val tStatus: Int,
    @SerializedName("inId")
    val inId: Int,
    @SerializedName("inTitle")
    val inTitle: String,
    @SerializedName("inDescription")
    val inDescription: String,
    @SerializedName("inStatus")
    val inStatus: Int
)

data class Tsec(
    @SerializedName("tsecId")
    val tsecId: Int,
    @SerializedName("testId")
    val testId: Int,
    @SerializedName("tsecName")
    val tsecName: String,
    @SerializedName("marks")
    val marks: String,
    @SerializedName("negMarks")
    val negMarks: String,
    @SerializedName("totalQuestions")
    val totalQuestions: Int,
    @SerializedName("tsecStatus")
    val tsecStatus: Int,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("created_at")
    val createdAt: String
)

//data class Que(
//    @SerializedName("qId")
//    val qId: Int,
//    @SerializedName("qTitle")
//    val qTitle: String,
//    @SerializedName("marks")
//    val marks: String,
//    @SerializedName("negMarks")
//    val negMarks: String,
//    @SerializedName("tsecId")
//    val tsecId: Int,
//    @SerializedName("qOptions")
//    val qOptions: QOptions,
//    @SerializedName("totalOptions")
//    val totalOptions: Int,
//    @SerializedName("qType")
//    val qType: String
//)

