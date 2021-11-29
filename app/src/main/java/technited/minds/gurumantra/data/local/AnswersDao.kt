package technited.minds.gurumantra.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import technited.minds.gurumantra.model.AnswersItem
import technited.minds.gurumantra.model.QuestionsItem

@Dao
interface AnswersDao {

    @Query("SELECT * FROM answers")
    fun getAnswersList(): LiveData<List<AnswersItem>>

    @Query("SELECT * FROM answers WHERE qId = :id")
    fun getAnswer(id: String): LiveData<AnswersItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(answers: List<AnswersItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(answersItem: AnswersItem)

    @Query("DELETE FROM answers")
    fun clearAll()


}