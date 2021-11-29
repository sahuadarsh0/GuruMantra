package technited.minds.gurumantra.model


import com.google.gson.annotations.SerializedName

data class TestDetails(
    @SerializedName("tsens")
    val tsEnrols: Int,
    @SerializedName("tss")
    val tss: TestSeriesItem,
    @SerializedName("users")
    val user: Users? = null
)