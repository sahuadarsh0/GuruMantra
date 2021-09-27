package technited.minds.gurumantra.model


import com.google.gson.annotations.SerializedName

data class LoginDetails(
    @SerializedName("data")
    val loginData: Data,
    @SerializedName("msg")
    val msg: String
)

data class Data(
    @SerializedName("name")
    val name: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("email")
    val email: String,
    @SerializedName("contact")
    val contact: String,
    @SerializedName("package")
    val packageX: Int,
    @SerializedName("type")
    val type: String,
    @SerializedName("image")
    val image: String
)