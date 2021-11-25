package technited.minds.gurumantra.model


import com.google.gson.annotations.SerializedName

data class GetNotes(
    @SerializedName("notes")
    val notes: List<Note>,
    @SerializedName("nts")
    val nts: String
)