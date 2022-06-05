package technited.minds.gurumantra.model


import com.google.gson.annotations.SerializedName

data class GetNotes(
    @SerializedName("notes")
    val notes: List<Note>,
    @SerializedName("nts")
    val nts: String? = null
)


data class Note(
    @SerializedName("noteId")
    val noteId: Int,
    @SerializedName("cId")
    val cId: Int,
    @SerializedName("scId")
    val scId: Int,
    @SerializedName("notesTitle")
    val notesTitle: String,
    @SerializedName("notesPDF")
    val notesPDF: String,
    @SerializedName("package")
    val packageX: Int,
    var userPackage: Int,
    @SerializedName("notesPrice")
    val notesPrice: Int,
    @SerializedName("notesValidity")
    val notesValidity: Int,
    @SerializedName("notesStatus")
    val notesStatus: Int,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("CreatedBY")
    val createdBY: Int,
    @SerializedName("packageName")
    val packageName: String
)