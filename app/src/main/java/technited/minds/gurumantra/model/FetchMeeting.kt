package technited.minds.gurumantra.model


import com.google.gson.annotations.SerializedName

data class FetchMeeting(
    @SerializedName("clsEnroll",alternate = ["lcsEnroll"])
    val clsEnroll: Int,
    @SerializedName("cls",alternate = ["lcs"])
    val cls: MeetingDetailsItem,
    @SerializedName("notices")
    val notices: List<Notice>
)