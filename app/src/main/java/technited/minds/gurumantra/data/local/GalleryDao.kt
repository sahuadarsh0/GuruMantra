package technited.minds.gurumantra.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import technited.minds.gurumantra.model.*

@Dao
interface GalleryDao {

    @Query("SELECT * FROM gallery")
    fun getGallery(): LiveData<List<Gal>>

    @Query("SELECT * FROM gallery WHERE gId = :id")
    fun getGalleryOne(id: String): LiveData<Gal>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(gallery: List<Gal>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(gal: Gal)

    @Query("DELETE FROM gallery")
    suspend fun clearAll()


}