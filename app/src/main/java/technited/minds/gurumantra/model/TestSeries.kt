package technited.minds.gurumantra.model


import com.google.gson.annotations.SerializedName

class TestSeries : ArrayList<TestSeriesItem>()

data class TestSeriesItem(
    @SerializedName(value = "tsId", alternate = ["praId","ptsId"])
    val tsId: Int,
    @SerializedName("cId")
    val cId: Int,
    @SerializedName("scId")
    val scId: Int,
    @SerializedName(value = "package", alternate = ["ptsPackage"])
    val packageX: Int,
    @SerializedName(value = "tsName", alternate = ["praName","ptsName"])
    val tsName: String,
    @SerializedName(value = "tsDescription", alternate = ["praDescription","ptsDescription"])
    val tsDescription: String,
    @SerializedName("totalTests")
    val totalTests: Int,
    @SerializedName("price", alternate = ["ptsPrice"])
    val price: Int,
    @SerializedName(value = "thumbnail", alternate = ["ptsImage"])
    val thumbnail: String,
    @SerializedName(value = "tsStatus", alternate = ["praStatus","ptsStatus"])
    val tsStatus: Int,
    @SerializedName(value = "tsEnrolls", alternate = ["praEnrolls","ptsEnrolls"])
    val tsEnrolls: Int,
    @SerializedName("cName")
    val cName: String,
    @SerializedName("scName")
    val scName: String,
    @SerializedName("packageName")
    val packageName: String
)