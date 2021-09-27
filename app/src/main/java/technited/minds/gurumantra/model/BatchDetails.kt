package technited.minds.gurumantra.model


import com.google.gson.annotations.SerializedName

class BatchDetails : ArrayList<BatchDetailsItem>()

data class BatchDetailsItem(
    @SerializedName("batchId")
    val batchId: Int,
    @SerializedName("batchName")
    val batchName: String,
    @SerializedName("batchType")
    val batchType: Int,
    @SerializedName("batchPackage")
    val batchPackage: Int,
    @SerializedName("batchPrice")
    val batchPrice: Int,
    @SerializedName("batchValidity")
    val batchValidity: Int,
    @SerializedName("batchImage")
    val batchImage: String,
    @SerializedName("batchDescription")
    val batchDescription: String,
    @SerializedName("createdBy")
    val createdBy: Int,
    @SerializedName("batchStatus")
    val batchStatus: Int,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String
)