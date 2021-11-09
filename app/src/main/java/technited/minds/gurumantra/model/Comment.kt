package technited.minds.gurumantra.model


import com.google.gson.annotations.SerializedName

data class Comment(
    @SerializedName("bcId")
    val bcId: Int,
    @SerializedName("blogId")
    val blogId: Int,
    @SerializedName("userId")
    val userId: Int,
    @SerializedName("bcComment")
    val bcComment: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("username")
    val username: String,
    @SerializedName("userType")
    val userType: String
)

data class CommentResponse(
    @SerializedName("msg")
    val msg: String,
    @SerializedName("data")
    val data: Int
)