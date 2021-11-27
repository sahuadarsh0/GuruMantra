package technited.minds.gurumantra.model


import com.google.gson.annotations.SerializedName

data class LoginDetails(
    @SerializedName("data")
    val loginData: Users,
    @SerializedName("msg")
    val msg: String
)