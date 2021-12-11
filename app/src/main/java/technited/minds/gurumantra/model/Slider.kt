package technited.minds.gurumantra.model


import com.google.gson.annotations.SerializedName

data class Slider(
    @SerializedName("id")
    val id: Int,
    @SerializedName("sliderImage")
    val sliderImage: String
)