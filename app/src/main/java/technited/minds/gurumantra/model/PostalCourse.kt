package technited.minds.gurumantra.model


import com.google.gson.annotations.SerializedName

data class PostalCourses(
    @SerializedName("pcs")
    val postalCourse: List<PostalCourseItem>
)

data class PostalCourseItem(
    @SerializedName("pcsId")
    val pcsId: Int,
    @SerializedName("pcsName")
    val pcsName: String,
    @SerializedName("pcsCatogery")
    val pcsCatogery: String,
    @SerializedName("pcsPrice")
    val pcsPrice: String,
    @SerializedName("pcsStock")
    val pcsStock: Int,
    @SerializedName("pcsAvail")
    val pcsAvail: Int,
    @SerializedName("pcsDescription")
    val pcsDescription: String,
    @SerializedName("pcsStatus")
    val pcsStatus: Int,
    @SerializedName("pcsCreatedBy")
    val pcsCreatedBy: Int,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String
)

data class SubmitPostalAddress(
    @SerializedName("pcsId")
    val pcsId: Int,
    @SerializedName("userId")
    val userId: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("city")
    val city: String,
    @SerializedName("state")
    val state: String,
    @SerializedName("district")
    val district: String,
    @SerializedName("pcode")
    val pcode: String,
    @SerializedName("address")
    val address: String
)


data class PostalResult(
    @SerializedName("pcs")
    val pcs: PCOrder // Temp
)


data class OrderPostalCourses(
    @SerializedName("pcs")
    val pcs: List<PCOrder>
)

data class PCOrder(
    @SerializedName("id")
    val id: Int,
    @SerializedName("pcsName")
    val pcsName: String,
    @SerializedName("amount")
    val amount: String,
    @SerializedName("orderStatus")
    val orderStatus: String
)
