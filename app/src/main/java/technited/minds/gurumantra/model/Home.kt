package technited.minds.gurumantra.model


import com.google.gson.annotations.SerializedName

data class Home(
    @SerializedName("blogs")
    val blogs: List<Blog>,
    @SerializedName("courses")
    val courses: List<Course>,
    @SerializedName("pdfts")
    val pdfts: List<TestSeriesItem>,
    @SerializedName("tss")
    val tss: List<TestSeriesItem>,
    @SerializedName("practs")
    val practs: List<TestSeriesItem>,
    @SerializedName("batches")
    val batches: List<BatchDetailsItem>,
    @SerializedName("notices")
    val notices: List<Notice>,
    @SerializedName("sliders")
    val sliders: List<Slider>,
    @SerializedName("smallsliders")
    val smallSliders: List<Slider>
)