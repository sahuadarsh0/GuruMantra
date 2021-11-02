package technited.minds.gurumantra.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "answers")
data class AnswersItem(

    @PrimaryKey
    @SerializedName("qId")
    val qId: Int,
    @SerializedName("tsecId")
    val tsecId: Int,
    @SerializedName("ans")
    val ans: Int
)
