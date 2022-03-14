package technited.minds.gurumantra.model


import com.google.gson.annotations.SerializedName

data class Coupons(
    @SerializedName("coupons")
    val coupons: List<Coupon>
)

data class Coupon(
    @SerializedName("ccId")
    val ccId: Int,
    @SerializedName("ccName")
    val ccName: String,
    @SerializedName("ccCode")
    val ccCode: String,
    @SerializedName("ccDiscount")
    val ccDiscount: Int,
    @SerializedName("ccValidity")
    val ccValidity: Int,
    @SerializedName("ccLimit")
    val ccLimit: Int,
    @SerializedName("ccFor")
    val ccFor: Int,
    @SerializedName("expired_at")
    val expiredAt: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("created_by")
    val createdBy: Int,
    @SerializedName("ccStatus")
    val ccStatus: Int
)