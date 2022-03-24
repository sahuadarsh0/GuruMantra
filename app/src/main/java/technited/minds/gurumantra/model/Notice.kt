package technited.minds.gurumantra.model


import com.google.gson.annotations.SerializedName

data class Notice(
    @SerializedName("ntId")
    val ntId: Int,
    @SerializedName("content",alternate = ["cnContent"])
    val content: String
)