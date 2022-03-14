package technited.minds.gurumantra.utils

enum class Constants(private val text: String) {

    URL("https://gurumantra.online/"),
    BASE_URL(URL.toString() +"api/"),

    ASA_URL("http://technitedminds.com/admin/");

    override fun toString(): String {
        return text
    }
}