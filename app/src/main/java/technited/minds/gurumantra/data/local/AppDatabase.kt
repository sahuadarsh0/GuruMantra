package technited.minds.gurumantra.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import technited.minds.gurumantra.model.AnswersItem
import technited.minds.gurumantra.model.QuestionsItem


@Database(entities = [QuestionsItem::class, AnswersItem::class], version = 1, exportSchema = false)
@TypeConverters(OptionsTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun questionsDao(): QuestionsDao
    abstract fun answersDao(): AnswersDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            instance ?: synchronized(this) { instance ?: buildDatabase(context).also { instance = it } }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, AppDatabase::class.java, "AICP")
                .fallbackToDestructiveMigration()
                .build()
    }

}