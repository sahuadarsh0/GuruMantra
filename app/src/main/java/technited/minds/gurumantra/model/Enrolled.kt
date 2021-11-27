package technited.minds.gurumantra.model


import com.google.gson.annotations.SerializedName

data class Enrolled(
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("tsId")
    val tsId: String
)