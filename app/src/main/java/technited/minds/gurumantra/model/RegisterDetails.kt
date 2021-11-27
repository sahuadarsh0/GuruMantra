package technited.minds.gurumantra.model


import com.google.gson.annotations.SerializedName

data class RegisterDetails(
    @SerializedName("message")
    val message: String,
    @SerializedName("users")
    val users: Users? = null
)