package technited.minds.gurumantra.model.test


import com.google.gson.annotations.SerializedName

data class Course(
    @SerializedName("courseId")
    val courseId: Int,
    @SerializedName("courseName")
    val courseName: String,
    @SerializedName("courseThumbnail")
    val courseThumbnail: String
)