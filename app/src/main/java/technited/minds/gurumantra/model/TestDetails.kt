package technited.minds.gurumantra.model


import com.google.gson.annotations.SerializedName

data class TestDetails(
    @SerializedName("tsens", alternate = ["ptsenrolls"])
    val tsEnrols: Int,
    @SerializedName("tss", alternate = ["pts"])
    val tss: TestSeriesItem,
    @SerializedName("users")
    val user: Users? = null
)