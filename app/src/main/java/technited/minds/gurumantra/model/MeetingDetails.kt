package technited.minds.gurumantra.model


import com.google.gson.annotations.SerializedName

data class MeetingDetails(
    @SerializedName("data",alternate = ["cls","lcs"])
    val details: List<MeetingDetailsItem>
)


data class MeetingDetailsItem(
    @SerializedName("classId",alternate = ["lcsId"])
    val classId: Int,
    @SerializedName("batchId")
    val batchId: Int,
    @SerializedName("className",alternate = ["lcsName"])
    val className: String,
    @SerializedName("classDescription",alternate = ["lcsDescription"])
    val classDescription: String,
    @SerializedName("thumbnail",alternate = ["lcsImage"])
    val thumbnail: String,
    @SerializedName("meetingId")
    val meetingId: String,
    @SerializedName("mPassword")
    val mPassword: String,
    @SerializedName("mePassword")
    val mePassword: String,
    @SerializedName("startTime")
    val startTime: String,
    @SerializedName("duration")
    val duration: Int,
    @SerializedName("hostEmail")
    val hostEmail: String,
    @SerializedName("startUrl")
    val startUrl: String,
    @SerializedName("joinUrl")
    val joinUrl: String,
    @SerializedName("createdBy")
    val createdBy: Int,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("enrolls")
    val enrolls: Int,
    @SerializedName("clStatus")
    val clStatus: Int,
    @SerializedName("batchName")
    val batchName: String,
    @SerializedName("batchPackage")
    val batchPackage: Int,
    @SerializedName("batchPrice")
    val batchPrice: Int,
    @SerializedName("userName")
    val userName: String
)