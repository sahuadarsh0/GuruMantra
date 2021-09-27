package technited.minds.gurumantra.utils

enum class Constants(private val text: String) {

    BASE_URL("https://gurumantra.online/api/"),

    ASSETS_URL(BASE_URL.toString() + "uploads/tipilogin/"),
    BANNER(ASSETS_URL.toString() + "app_home_slider/"),
    GALLERY(ASSETS_URL.toString() + "app_gallery_image/"),
    PHOTO(ASSETS_URL.toString() + "photo/");

    override fun toString(): String {
        return text
    }
}