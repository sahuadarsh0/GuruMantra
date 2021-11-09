package technited.minds.gurumantra.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import technited.minds.gurumantra.model.AnswersItem
import technited.minds.gurumantra.model.Blog
import technited.minds.gurumantra.model.QuestionsItem

@Dao
interface BlogsDao {

    @Query("SELECT * FROM blogs")
    fun getBlogs(): LiveData<List<Blog>>

    @Query("SELECT * FROM blogs WHERE blogId = :id")
    fun getBlog(id: String): LiveData<Blog>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(blogs: List<Blog>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(blog: Blog)

    @Query("DELETE FROM blogs")
    suspend fun clearAll()


}