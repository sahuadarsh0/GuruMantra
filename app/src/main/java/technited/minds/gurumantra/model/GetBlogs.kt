package technited.minds.gurumantra.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class GetBlogs(
    @SerializedName("blogs")
    val blogs: List<Blog>,

    @SerializedName("dcs")
    val dcs: List<Dcs>

)

data class GetDcs(
    @SerializedName("dcs")
    val dcs: Dcs

)

data class GetDcsComment(
    @SerializedName("comments")
    val comment:  List<Comment>

)


@Entity(tableName = "blogs")
data class Blog(
    @PrimaryKey
    @SerializedName("blogId")
    val blogId: Int,
    @SerializedName("cId")
    val cId: Int,
    @SerializedName("scId")
    val scId: Int,
    @SerializedName("blogTitle")
    val blogTitle: String,
    @SerializedName("blogShortContent")
    val blogShortContent: String,
    @SerializedName("blogContent")
    val blogContent: String,
    @SerializedName("blogThumbnail")
    val blogThumbnail: String,
    @SerializedName("blogCreatedBy")
    val blogCreatedBy: Int,
    @SerializedName("blogStatus")
    val blogStatus: Int,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("userName")
    val userName: String
)

data class Dcs(
    @SerializedName("dId")
    val dId: Int,
    @SerializedName("userId")
    val userId: Int,
    @SerializedName("bContent")
    val content: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("userName")
    val userName: String,
    @SerializedName("userImage")
    val userImage: String
)
