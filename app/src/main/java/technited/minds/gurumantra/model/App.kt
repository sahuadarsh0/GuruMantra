package technited.minds.gurumantra.model


import com.google.gson.annotations.SerializedName

data class App(
    @SerializedName("appVersion")
    val appVersion: String
)