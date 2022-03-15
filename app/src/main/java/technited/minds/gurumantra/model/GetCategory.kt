package technited.minds.gurumantra.model


import com.google.gson.annotations.SerializedName

data class GetCategory(
    @SerializedName("cts")
    val cts: List<Ct>
)

data class GetSubCategory(
    @SerializedName("scts")
    val scts: List<Sct>
)

data class Ct(
    @SerializedName("cId")
    val cId: Int,
    @SerializedName("cName")
    val cName: String,
    @SerializedName("cStatus")
    val cStatus: Int,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String
)


data class Sct(
    @SerializedName("scId")
    val scId: Int,
    @SerializedName("scName")
    val scName: String,
    @SerializedName("cId")
    val cId: Int,
    @SerializedName("scStatus")
    val scStatus: Int,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String
)