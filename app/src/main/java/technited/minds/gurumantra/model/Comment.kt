package technited.minds.gurumantra.model


import com.google.gson.annotations.SerializedName

data class Comment(
    @SerializedName("bcId", alternate = ["dcId","pccId","lccId"])
    val bcId: Int,
    @SerializedName("blogId", alternate = ["dId","pcId","lcId","lectureId"])
    val blogId: Int,
    @SerializedName("userId")
    val userId: Int,
    @SerializedName("bcComment", alternate = ["dcComment","pcComment","lcComment"])
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