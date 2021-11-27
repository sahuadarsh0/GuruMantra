package technited.minds.gurumantra.data.repository

import technited.minds.gurumantra.data.local.BlogsDao
import technited.minds.gurumantra.data.local.GalleryDao
import technited.minds.gurumantra.data.remote.RemoteDataSource
import technited.minds.gurumantra.model.EndTest
import technited.minds.gurumantra.model.QuestionsItem
import technited.minds.gurumantra.utils.performGetOperation
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localGalleryDataSource: GalleryDao,
    private val localBlogsDataSource: BlogsDao
) {
//    fun getMembersList() = performGetOperation(
//        databaseQuery = { localDataSource.getMembersList() },
//        networkCall = { remoteDataSource.getMembersList() },
//        saveCallResult = { localDataSource.insertAll(it) })

    suspend fun getBatches() = remoteDataSource.getBatches()
    suspend fun getMeetings(batchNo: String) = remoteDataSource.getMeetings(batchNo)
    suspend fun getBatchDetails(batchNo: String) = remoteDataSource.getBatchDetails(batchNo)
    suspend fun getFetchMeeting(classNo: String) = remoteDataSource.getFetchMeeting(classNo)

    suspend fun login(username: String, password: String) = remoteDataSource.login(username, password)
    suspend fun register(
        name: String,
        email: String,
        contact: String,
        password: String
    ) = remoteDataSource.register(name, email, contact, password)


    //Test Series
    suspend fun getTestSeries() = remoteDataSource.getTestSeries()
    suspend fun getTestSeriesDetails(tsId: String, uId: String) = remoteDataSource.getTestSeriesDetails(tsId, uId)
    suspend fun getListTests(tsId: String) = remoteDataSource.getListTests(tsId)
    suspend fun getStartTest(tId: String, uId: String) = remoteDataSource.getStartTest(tId, uId)
    suspend fun submitTest(endTest: EndTest) = remoteDataSource.submitTest(endTest)



    //Practice Sets
    suspend fun getSetSeries() = remoteDataSource.getSetSeries()
//    suspend fun getTestSeriesDetails(tsId: String, uId: String) = remoteDataSource.getTestSeriesDetails(tsId, uId)
//    suspend fun getListTests(tsId: String) = remoteDataSource.getListTests(tsId)
//    suspend fun getStartTest(tId: String, uId: String) = remoteDataSource.getStartTest(tId, uId)
//    suspend fun submitTest(endTest: EndTest) = remoteDataSource.submitTest(endTest)


    //    Blogs
    fun getBlogs() = performGetOperation(
        databaseQuery = { localBlogsDataSource.getBlogs() },
        networkCall = { remoteDataSource.getBlogs() },
        saveCallResult = { localBlogsDataSource.insertAll(it.blogs) }
    )

    suspend fun getComments(blogId: String) = remoteDataSource.getComments(blogId)
    suspend fun postComment(userId: Int, blogId: Int, comment: String) =
        remoteDataSource.postComment(userId, blogId, comment)

    // Others
    fun getGallery() = performGetOperation(
        databaseQuery = { localGalleryDataSource.getGallery() },
        networkCall = { remoteDataSource.getGallery() },
        saveCallResult = { localGalleryDataSource.insertAll(it.gals) }
    )

    suspend fun getPackages() = remoteDataSource.getPackages()
    suspend fun getPaymentData(userId: String, pckId: String) = remoteDataSource.getPaymentData(userId, pckId)

}