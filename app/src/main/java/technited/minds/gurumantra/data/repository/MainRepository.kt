package technited.minds.gurumantra.data.repository

import technited.minds.gurumantra.data.remote.RemoteDataSource
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {
//    fun getMembersList() = performGetOperation(
//        databaseQuery = { localDataSource.getMembersList() },
//        networkCall = { remoteDataSource.getMembersList() },
//        saveCallResult = { localDataSource.insertAll(it) })

//    fun getGallery() = performGetOperation(
//        databaseQuery = { galleryDataSource.getGallery() },
//        networkCall = { remoteDataSource.getGallery() },
//        saveCallResult = { galleryDataSource.insertAll(it) })

    suspend fun getBatches() = remoteDataSource.getBatches()
    suspend fun getMeetings(batchNo: String) = remoteDataSource.getMeetings(batchNo)
    suspend fun getBatchDetails(batchNo: String) = remoteDataSource.getBatchDetails(batchNo)
    suspend fun getFetchMeeting(classNo: String) = remoteDataSource.getFetchMeeting(classNo)

    //    suspend fun getMemberProfile(memberId: String) = remoteDataSource.getMemberProfile(memberId)
    suspend fun login(username: String, password: String) = remoteDataSource.login(username, password)

}