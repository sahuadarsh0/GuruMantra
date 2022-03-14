package technited.minds.gurumantra.model


import com.google.gson.annotations.SerializedName

data class Grant(
    @SerializedName("grant")
    val grant: Boolean
)