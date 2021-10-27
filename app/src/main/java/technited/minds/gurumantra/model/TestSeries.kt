package technited.minds.gurumantra.model


import com.google.gson.annotations.SerializedName

class TestSeries : ArrayList<TestSeriesItem>()

data class TestSeriesItem(
    @SerializedName("tsId")
    val tsId: Int,
    @SerializedName("cId")
    val cId: Int,
    @SerializedName("scId")
    val scId: Int,
    @SerializedName("package")
    val packageX: Int,
    @SerializedName("tsName")
    val tsName: String,
    @SerializedName("tsDescription")
    val tsDescription: String,
    @SerializedName("totalTests")
    val totalTests: Int,
    @SerializedName("price")
    val price: Int,
    @SerializedName("thumbnail")
    val thumbnail: String,
    @SerializedName("tsStatus")
    val tsStatus: Int,
    @SerializedName("tsCreatedBy")
    val tsCreatedBy: Int,
    @SerializedName("tsEnrolls")
    val tsEnrolls: Int,
    @SerializedName("tsMetaKey")
    val tsMetaKey: String,
    @SerializedName("tsMetaDesc")
    val tsMetaDesc: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("packageName")
    val packageName: String
)