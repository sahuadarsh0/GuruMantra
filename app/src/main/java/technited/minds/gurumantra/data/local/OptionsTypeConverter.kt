package technited.minds.gurumantra.data.local

import androidx.room.TypeConverter
import org.json.JSONObject
import technited.minds.gurumantra.model.QOptions

class OptionsTypeConverter {
    @TypeConverter
    fun fromOption(qOptions: QOptions): String {
        return JSONObject().apply {
            put("option1", qOptions.option1)
            put("option2", qOptions.option2)
            put("option3", qOptions.option3)
            put("option4", qOptions.option4)
            put("option5", qOptions.option5)
        }.toString()
    }

    @TypeConverter
    fun toOption(option: String): QOptions {
        val json = JSONObject(option)
        return QOptions(
            json.getString("option1"),
            json.getString("option2"),
            json.getString("option3"),
            json.getString("option4"),
            json.getString("option5")
        )
    }
}