package technited.minds.gurumantra.data.repository

import technited.minds.gurumantra.data.local.BlogsDao
import technited.minds.gurumantra.data.local.GalleryDao
import technited.minds.gurumantra.data.remote.RemoteDataSource
import technited.minds.gurumantra.model.EndTest
import technited.minds.gurumantra.model.SubmitPostalAddress
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
    suspend fun getSpecialOffers() = remoteDataSource.getSpecialOffers()


    //    Live Class
//    suspend fun getBatches() = remoteDataSource.getBatches()
//    suspend fun getMeetings(batchNo: String) = remoteDataSource.getMeetings(batchNo)
//    suspend fun getBatchDetails(batchNo: String) = remoteDataSource.getBatchDetails(batchNo)
//    suspend fun getFetchMeeting(classNo: String) = remoteDataSource.getFetchMeeting(classNo)

    //    Live Class v2
    suspend fun getBatches(type: String) = remoteDataSource.getBatches(type)
    suspend fun getMeetings(batchId: String, type: Int) = when (type) {
        0 -> remoteDataSource.getConfList(batchId) // "conference"
        1 -> remoteDataSource.getLiveList(batchId)// "live"
        else -> Resource(Resource.Status.SUCCESS, null, null)
    }

    suspend fun getClassDescription(userId: String, id: String, type: Int) = when (type) {
        0 -> remoteDataSource.getConfClassDesc(userId, id) // "conference"
        1 -> remoteDataSource.getLiveClassDesc(userId, id) // "live"
        else -> Resource(Resource.Status.SUCCESS, null, null)
    }

    suspend fun getPreviousClasses(id: String, type: Int) = when (type) {
        0 -> remoteDataSource.getConfPreviousClasses(id) // "conference"
        1 -> remoteDataSource.getLivePreviousClasses(id) // "live"
        else -> Resource(Resource.Status.SUCCESS, null, null)
    }

    suspend fun getBatchDetails(userId: String, batchId: String) = remoteDataSource.getBatchDetails(userId, batchId)
    suspend fun getJoinLiveClass(lcsId: String) = remoteDataSource.getJoinLiveClass(lcsId)


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
        "batch" -> remoteDataSource.getEnrolledBatch(userId, id)
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


    //    Blogs
    suspend fun getBlogs() = remoteDataSource.getBlogs()

//    suspend fun getComments(blogId: String) = remoteDataSource.getComments(blogId)
//    suspend fun postComment(userId: Int, blogId: Int, comment: String) =
//        remoteDataSource.postComment(userId, blogId, comment)

    suspend fun getDiscussionForumDetail(dId: String) = remoteDataSource.getDiscussionForumDetail(dId)

    //    suspend fun getDiscussionComments(dId: String) = remoteDataSource.getDiscussionComments(dId)
//    suspend fun postDiscussionComment(userId: Int, dId: Int, comment: String) =
//        remoteDataSource.postDiscussionComment(userId, dId, comment)

    suspend fun filterBlogs(cId: String, scId: String) = remoteDataSource.filterBlogs(cId, scId)

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

    suspend fun filterNotes(cId: String, scId: String) = remoteDataSource.filterNotes(cId, scId)

    suspend fun getPackages() = remoteDataSource.getPackages()
    suspend fun getCoupons(userId: String) = remoteDataSource.getCoupons(userId)
    suspend fun getPaymentData(userId: String, id: String, type: String, coupon: String? = null) = when (type) {
        "package" -> remoteDataSource.getPaymentDataPackage(userId, id, coupon)
        "test" -> remoteDataSource.getPaymentDataSeries(userId, id, coupon)
        "practice" -> remoteDataSource.getPaymentDataPractice(userId, id, coupon)
        "course" -> remoteDataSource.getPaymentDataCourse(userId, id, coupon)
        "batch" -> remoteDataSource.getPaymentDataBatch(userId, id, coupon)
        else -> remoteDataSource.getPaymentDataPackage(userId, id, coupon)
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
        "batch" -> remoteDataSource.purchaseBatch(userId, orderId, paymentId)
        "postal" -> remoteDataSource.purchasePostal(userId, orderId, paymentId)
        else -> remoteDataSource.purchasePackage(userId, orderId, paymentId)
    }


    suspend fun getComments(
        id: String,
        type: String
    ) = when (type) {
        "blog" -> remoteDataSource.getComments(id)
        "discussion" -> remoteDataSource.getDiscussionComments(id)
        "conference" -> remoteDataSource.getConfComments(id)
        "live" -> remoteDataSource.getLiveComments(id)
        else -> remoteDataSource.getComments(id)
    }


    suspend fun postComment(
        userId: Int,
        id: Int,
        comment: String,
        type: String
    ) = when (type) {
        "blog" -> remoteDataSource.postComment(userId, id, comment)
        "discussion" -> remoteDataSource.postDiscussionComment(userId, id, comment)
        "conference" -> remoteDataSource.postConfComment(userId, id, comment)
        "live" -> remoteDataSource.postLiveComment(userId, id, comment)
        else -> remoteDataSource.postComment(userId, id, comment)
    }


    // Courses
    suspend fun getCourses() = remoteDataSource.getCourses()
    suspend fun getCourseDetails(userId: String, cid: String) =
        remoteDataSource.getCourseDetails(userId, cid)

    //    Postal Courses
    suspend fun getPostalCourses() = remoteDataSource.getPostalCourses()
    suspend fun submitPostalAddress(submitPostalAddress: SubmitPostalAddress) =
        remoteDataSource.submitPostalAddress(submitPostalAddress)

    suspend fun getPostalOrders(userId: String) = remoteDataSource.getPostalOrders(userId)
}