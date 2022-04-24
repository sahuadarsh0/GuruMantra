package technited.minds.gurumantra.model


import com.google.gson.annotations.SerializedName

data class GetCourses(
    @SerializedName("courses")
    val courses: List<Course>
)

data class Course(
    @SerializedName("courseId")
    val courseId: Int,
    @SerializedName("courseName")
    val courseName: String,
    @SerializedName("cId")
    val cId: Int,
    @SerializedName("scId")
    val scId: Int,
    @SerializedName("pid")
    val pid: Int,
    @SerializedName("coursePrice")
    val coursePrice: Int,
    @SerializedName("courseValidity")
    val courseValidity: Any,
    @SerializedName("courseCreatedBy")
    val courseCreatedBy: Int,
    @SerializedName("courseStartDate")
    val courseStartDate: String,
    @SerializedName("courseEndDate")
    val courseEndDate: Any,
    @SerializedName("courseTimeout")
    val courseTimeout: Int,
    @SerializedName("courseDescription")
    val courseDescription: String,
    @SerializedName("courseThumbnail")
    val courseThumbnail: String,
    @SerializedName("courseStatus")
    val courseStatus: Int,
    @SerializedName("courseEnrolls")
    val courseEnrolls: Int,
    @SerializedName("courseMetaKey")
    val courseMetaKey: String,
    @SerializedName("courseMetaDesc")
    val courseMetaDesc: String,
    @SerializedName("expired_at")
    val expiredAt: Any,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("creatorName")
    val creatorName: String
)