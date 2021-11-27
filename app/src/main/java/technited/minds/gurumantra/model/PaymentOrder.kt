package technited.minds.gurumantra.model


import com.google.gson.annotations.SerializedName


data class PaymentOrder(
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("datas")
    val data: PaymentData,
    @SerializedName("pcks")
    val pck: Pck,
    @SerializedName("tss")
    val tss: TestSeriesItem
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