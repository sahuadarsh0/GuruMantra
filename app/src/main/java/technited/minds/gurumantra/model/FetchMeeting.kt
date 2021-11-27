package technited.minds.gurumantra.model


import com.google.gson.annotations.SerializedName

data class FetchMeeting(
    @SerializedName("cls")
    val cls: MeetingDetailsItem,
    @SerializedName("notices")
    val notices: List<String>
)