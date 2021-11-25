package technited.minds.gurumantra.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "questions")
data class QuestionsItem(

    @PrimaryKey
    @SerializedName("qId")
    val qId: Int,
    @SerializedName("qTitle")
    val qTitle: String,
    @SerializedName("marks")
    val marks: String,
    @SerializedName("negMarks")
    val negMarks: String,
    @SerializedName("tsecId")
    val tsecId : Int? = null,
    @SerializedName("qOptions")
    val qOptions: QOptions,
    @SerializedName("totalOptions")
    val totalOptions: Int,
    @SerializedName("correctOptions")
    val correctOptions: String? = null,
    @SerializedName("qType")
    val qType: String
)

data class QOptions(
    @SerializedName("option1")
    val option1: String,
    @SerializedName("option2")
    val option2: String,
    @SerializedName("option3")
    val option3: String,
    @SerializedName("option4")
    val option4: String,
    @SerializedName("option5")
    val option5: String
)