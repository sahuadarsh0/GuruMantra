package technited.minds.gurumantra.model


import com.google.gson.annotations.SerializedName

data class JoinLiveClass(
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("cls")
    val cls: PreviousClassItem,
    @SerializedName("lcs")
    val lcs: MeetingDetailsItem
)

