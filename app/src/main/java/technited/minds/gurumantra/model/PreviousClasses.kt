package technited.minds.gurumantra.model


import com.google.gson.annotations.SerializedName

data class PreviousClasses(
    @SerializedName("cls")
    val cls: MeetingDetailsItem,
    @SerializedName("previousClasses")
    val previousClasses: List<PreviousClassItem>
)


data class PreviousClassItem(
    @SerializedName("pcId",alternate = ["lcId"])
    val pcId: Int,
    @SerializedName("cId",alternate = ["lcsId"])
    val cId: Int,
    @SerializedName("pcName",alternate = ["lcName"])
    val pcName: String,
    @SerializedName("pcImage",alternate = ["lcImage"])
    val pcImage: String,
    @SerializedName("pcVideo",alternate = ["lcVideoId"])
    val pcVideo: String,
    @SerializedName("pcCreatedBy",alternate = ["createdBy"])
    val pcCreatedBy: Int,

    @SerializedName("lcDescription")
    val lcDescription: String,

    @SerializedName("lcStartTime")
    val lcStartTime: String,

    @SerializedName("lcContent")
    val lcContent: String,

    @SerializedName("lcPdf")
    val lcPdf: String,

    @SerializedName("created_at")
    val createdAt: String,

    @SerializedName("updated_at")
    val updatedAt: String)