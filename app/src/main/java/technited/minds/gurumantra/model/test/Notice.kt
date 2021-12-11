package technited.minds.gurumantra.model.test


import com.google.gson.annotations.SerializedName

data class Notice(
    @SerializedName("ntId")
    val ntId: Int,
    @SerializedName("content")
    val content: String
)