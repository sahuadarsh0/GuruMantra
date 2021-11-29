package technited.minds.gurumantra.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import technited.minds.gurumantra.model.QuestionsItem

@Dao
interface QuestionsDao {

    @Query("SELECT * FROM questions")
    fun getQuestionsList(): LiveData<List<QuestionsItem>>

    @Query("SELECT * FROM questions WHERE qId = :id")
    fun getQuestion(id: String): LiveData<QuestionsItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(questions: List<QuestionsItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(questionsItem: QuestionsItem)

    @Query("DELETE FROM questions")
    fun clearAll()


}