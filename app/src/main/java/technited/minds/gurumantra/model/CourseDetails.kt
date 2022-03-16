package technited.minds.gurumantra.model


import com.google.gson.annotations.SerializedName

data class CourseDetails(
    @SerializedName("courses")
    val courses: Course,
    @SerializedName("modules")
    val modules: List<Module>,
    @SerializedName("enrolls")
    val enrolls: String,
    @SerializedName("user")
    val user: Users? = null
)



data class Module(
    @SerializedName("moduleId")
    val moduleId: Int,
    @SerializedName("moduleTitle")
    val moduleTitle: String,
    @SerializedName("lectures")
    val lectures: List<Lecture>
)

data class Lecture(
    @SerializedName("lectureId")
    val lectureId: Int,
    @SerializedName("courseId")
    val courseId: Int,
    @SerializedName("moduleId")
    val moduleId: Int,
    @SerializedName("lectureTitle")
    val lectureTitle: String,
    @SerializedName("lectureVideo")
    val lectureVideo: String,
    @SerializedName("lectureContent")
    val lectureContent: String,
    @SerializedName("lectureStatus")
    val lectureStatus: Int,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String
)