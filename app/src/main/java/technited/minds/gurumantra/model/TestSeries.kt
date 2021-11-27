package technited.minds.gurumantra.model


import com.google.gson.annotations.SerializedName

class TestSeries : ArrayList<TestSeriesItem>()

data class TestSeriesItem(
    @SerializedName(value = "tsId", alternate = ["praId"])
    val tsId: Int,
    @SerializedName("cId")
    val cId: Int,
    @SerializedName("scId")
    val scId: Int,
    @SerializedName("package")
    val packageX: Int,
    @SerializedName(value = "tsName", alternate = ["praName"])
    val tsName: String,
    @SerializedName(value = "tsDescription", alternate = ["praDescription"])
    val tsDescription: String,
    @SerializedName("totalTests")
    val totalTests: Int,
    @SerializedName("price")
    val price: Any,
    @SerializedName("thumbnail")
    val thumbnail: String,
    @SerializedName(value = "tsStatus", alternate = ["praStatus"])
    val tsStatus: Int,
    @SerializedName(value = "tsEnrolls", alternate = ["praEnrolls"])
    val tsEnrolls: Int,
    @SerializedName("cName")
    val cName: String,
    @SerializedName("scName")
    val scName: String,
    @SerializedName("packageName")
    val packageName: String
)