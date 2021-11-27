package technited.minds.gurumantra.model


import com.google.gson.annotations.SerializedName

data class Users(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("contact")
    val contact: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("status")
    val status: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("package")
    val packageX: Int,
    @SerializedName("startDate")
    val startDate: Any,
    @SerializedName("expireDate")
    val expireDate: Any,
    @SerializedName("otp")
    val otp: Int,
    @SerializedName("emailOTP")
    val emailOTP: Any,
    @SerializedName("phoneVerified")
    val phoneVerified: Int,
    @SerializedName("email_verified")
    val emailVerified: Int
)