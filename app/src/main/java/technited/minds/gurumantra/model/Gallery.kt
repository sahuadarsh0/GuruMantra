package technited.minds.gurumantra.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class Gallery(
    @SerializedName("gals")
    val gals: List<Gal>
)

@Entity(tableName = "gallery")
data class Gal(

    @PrimaryKey
    @SerializedName("gId")
    val gId: Int,
    @SerializedName("gTitle")
    val gTitle: String,
    @SerializedName("gImage")
    val gImage: String
)