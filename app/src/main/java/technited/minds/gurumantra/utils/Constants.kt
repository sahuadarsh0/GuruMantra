package technited.minds.gurumantra.utils

enum class Constants(private val text: String) {

    URL("https://gurumantra.online/"),
    BASE_URL(URL.toString() +"api/"),

    PHOTO(BASE_URL.toString() + "photo/");

    override fun toString(): String {
        return text
    }
}