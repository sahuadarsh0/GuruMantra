package technited.minds.gurumantra.model


import com.google.gson.annotations.SerializedName

data class GetLibraryNotes(
    @SerializedName("notes")
    val notes: List<Note>,
    @SerializedName("smnts")
    val smnts: List<Note>,
    @SerializedName("ncerts")
    val ncerts: List<Note>,
    @SerializedName("cas")
    val cas: List<Note>,
    @SerializedName("vss")
    val vss: Boolean
)