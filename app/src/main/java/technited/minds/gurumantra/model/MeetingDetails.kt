package technited.minds.gurumantra.model


import com.google.gson.annotations.SerializedName

data class MeetingDetails(
    @SerializedName("data")
    val details : List<Details>
)


data class Details(
    @SerializedName("classId")
    val classId: Int,
    @SerializedName("batchId")
    val batchId: Int,
    @SerializedName("className")
    val className: String,
    @SerializedName("classDescription")
    val classDescription: String,
    @SerializedName("thumbnail")
    val thumbnail: String,
    @SerializedName("meetingId")
    val meetingId: String,
    @SerializedName("mPassword")
    val mPassword: String,
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
    @SerializedName("userName")
    val userName: String
)