package technited.minds.gurumantra.model


import com.google.gson.annotations.SerializedName


data class PaymentOrder(
    @SerializedName("datas")
    val data: PaymentData,
    @SerializedName("pcks")
    val pck: Pck
)
data class PaymentData(
    @SerializedName("orderId")
    val orderId: String,
    @SerializedName("amount")
    val amount: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("key")
    val key: String
)