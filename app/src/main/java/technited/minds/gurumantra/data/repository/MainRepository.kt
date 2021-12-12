package technited.minds.gurumantra.data.repository

import technited.minds.gurumantra.data.local.BlogsDao
import technited.minds.gurumantra.data.local.GalleryDao
import technited.minds.gurumantra.data.remote.RemoteDataSource
import technited.minds.gurumantra.model.EndTest
import technited.minds.gurumantra.utils.Resource
import technited.minds.gurumantra.utils.performGetOperation
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localGalleryDataSource: GalleryDao,
    private val localBlogsDataSource: BlogsDao
) {

    //  Home
    suspend fun getHome() = remoteDataSource.getHome()


    //    Live Class
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


    suspend fun getEnrolled(userId: String, id: String, type: String) = when (type) {
        "test" -> remoteDataSource.getEnrolled(userId, id)
        "practice" -> remoteDataSource.getEnrolledSet(userId, id)
        else -> Resource(Resource.Status.SUCCESS, null, null)
    }

    //Test Series & Practice Sets
    suspend fun getTestSeries(type: String) = when (type) {
        "test" -> remoteDataSource.getTestSeries()
        "practice" -> remoteDataSource.getSetSeries()
        "pdf" -> remoteDataSource.getPDF()
        else -> remoteDataSource.getTestSeries()
    }

    suspend fun getTestSeriesDetails(userId: String, id: String, type: String) = when (type) {
        "test" -> remoteDataSource.getTestSeriesDetails(userId, id)
        "practice" -> remoteDataSource.getSetSeriesDetails(userId, id)
//        "pdf" -> remoteDataSource.getPDFDetails(id)
        else -> Resource(Resource.Status.SUCCESS, null, null)
    }

    suspend fun getListTests(id: String, type: String) = when (type) {
        "test" -> remoteDataSource.getListTests(id)
        "practice" -> remoteDataSource.getListSets(id)
        "pdf" -> remoteDataSource.getPDFDetails(id)
        else -> Resource(Resource.Status.SUCCESS, null, null)
    }

    suspend fun getStartTest(uId: String, id: String, type: String) = when (type) {
        "test" -> remoteDataSource.getStartTest(uId, id)
        "practice" -> remoteDataSource.getStartSet(uId, id)
        else -> remoteDataSource.getStartTest(uId, id)
    }

    suspend fun submitTest(endTest: EndTest) = remoteDataSource.submitTest(endTest)


    //    PDF Tests
    suspend fun getPDF() = remoteDataSource.getPDF()

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
    suspend fun getPaymentData(userId: String, id: String, type: String) = when (type) {
        "package" -> remoteDataSource.getPaymentDataPackage(userId, id)
        "test" -> remoteDataSource.getPaymentDataSeries(userId, id)
        "practice" -> remoteDataSource.getPaymentDataPractice(userId, id)
        else -> remoteDataSource.getPaymentDataPackage(userId, id)
    }

    suspend fun purchase(
        userId: String,
        orderId: String,
        paymentId: String,
        type: String
    ) = when (type) {
        "package" -> remoteDataSource.purchasePackage(userId, orderId, paymentId)
        "test" -> remoteDataSource.purchaseSeries(userId, orderId, paymentId)
        "practice" -> remoteDataSource.purchasePractice(userId, orderId, paymentId)
        else -> remoteDataSource.purchasePackage(userId, orderId, paymentId)
    }

}