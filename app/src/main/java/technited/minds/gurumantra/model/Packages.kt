package technited.minds.gurumantra.model


import com.google.gson.annotations.SerializedName

data class Packages(
    @SerializedName("pcks")
    val pcks: List<Pck>
)

data class Pck(
    @SerializedName("pckId")
    val pckId: Int,
    @SerializedName("pId")
    val pId: Int,
    @SerializedName("pckName")
    val pckName: String,
    @SerializedName("pckType")
    val pckType: String,
    @SerializedName("pckMRP")
    val pckMRP: Int,
    @SerializedName("pckPrice")
    val pckPrice: Int,
    @SerializedName("pckOffPercent")
    val pckOffPercent: Int,
    @SerializedName("pckValidity")
    val pckValidity: Int,
    @SerializedName("pckStatus")
    val pckStatus: Int,
    @SerializedName("expired_at")
    val expiredAt: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("created_at")
    val createdAt: String
)