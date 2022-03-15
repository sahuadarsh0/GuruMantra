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
        "course" -> remoteDataSource.getEnrolledCourse(userId, id)
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
    suspend fun getBlogs() = remoteDataSource.getBlogs()

    suspend fun getComments(blogId: String) = remoteDataSource.getComments(blogId)
    suspend fun postComment(userId: Int, blogId: Int, comment: String) =
        remoteDataSource.postComment(userId, blogId, comment)
    suspend fun getDiscussionForumDetail(dId: String) = remoteDataSource.getDiscussionForumDetail(dId)
    suspend fun getDiscussionComments(dId: String) = remoteDataSource.getDiscussionComments(dId)
    suspend fun postDiscussionComment(userId: Int, dId: Int, comment: String) =
        remoteDataSource.postDiscussionComment(userId, dId, comment)
    suspend fun filterBlogs(cId: String,scId: String) = remoteDataSource.filterBlogs(cId, scId)

    // Others
    fun getGallery() = performGetOperation(
        databaseQuery = { localGalleryDataSource.getGallery() },
        networkCall = { remoteDataSource.getGallery() },
        saveCallResult = { localGalleryDataSource.insertAll(it.gals) }
    )

    suspend fun getCategory() = remoteDataSource.getCategory()
    suspend fun getSubCategory(cid: String) = remoteDataSource.getSubCategory(cid)

    suspend fun getLibraryNotes() = remoteDataSource.getLibraryNotes()
    suspend fun getNotes(type: String) = when (type) {
        "Sample" -> remoteDataSource.getSampleNotes()
        "Ca" -> remoteDataSource.getCaNotes()
        "Ncert" -> remoteDataSource.getNcertNotes()
        "All" -> remoteDataSource.getAllNotes()
        else -> remoteDataSource.getAllNotes()
    }
    suspend fun filterNotes(cId: String,scId: String) = remoteDataSource.filterNotes(cId, scId)

    suspend fun getPackages() = remoteDataSource.getPackages()
    suspend fun getCoupons(userId: String) = remoteDataSource.getCoupons(userId)
    suspend fun getPaymentData(userId: String, id: String, type: String, coupon: String? = null) = when (type) {
        "package" -> remoteDataSource.getPaymentDataPackage(userId, id,coupon)
        "test" -> remoteDataSource.getPaymentDataSeries(userId, id)
        "practice" -> remoteDataSource.getPaymentDataPractice(userId, id)
        "course" -> remoteDataSource.getPaymentDataCourse(userId, id)
        else -> remoteDataSource.getPaymentDataPackage(userId, id,coupon)
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
        "course" -> remoteDataSource.purchaseCourse(userId, orderId, paymentId)
        else -> remoteDataSource.purchasePackage(userId, orderId, paymentId)
    }


    // Courses
    suspend fun getCourses() = remoteDataSource.getCourses()
    suspend fun getCourseDetails(userId: String, cid: String) =
        remoteDataSource.getCourseDetails(userId, cid)

}