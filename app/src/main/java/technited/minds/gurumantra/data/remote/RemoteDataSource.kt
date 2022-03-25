package technited.minds.gurumantra.data.remote

import technited.minds.gurumantra.model.EndTest
import technited.minds.gurumantra.model.SubmitPostalAddress
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiService: ApiService) : BaseDataSource() {
    //    Home
    suspend fun getHome() = getResult { apiService.getHome() }
    suspend fun getSpecialOffers() = getResult { apiService.getSpecialOffers() }

    //    Live Class
//    suspend fun getMeetings(batchNo: String) = getResult { apiService.getMeetings(batchNo) }
//    suspend fun getBatchDetails(batchNo: String) = getResult { apiService.getBatchDetails(batchNo) }
//    suspend fun getFetchMeeting(classNo: String) = getResult { apiService.getFetchMeeting(classNo) }
//    suspend fun getBatches(type: String) = getResult { apiService.getBatches(type) }

    //    Live Class v2
    suspend fun getBatches(type: String) = getResult { apiService.getBatches(type) }
    suspend fun getConfList(batchId: String) = getResult { apiService.getConfList(batchId) }
    suspend fun getLiveList(batchId: String) = getResult { apiService.getLiveList(batchId) }
    suspend fun getBatchDetails(userId: String, batchId: String) =
        getResult { apiService.getBatchDetails(userId, batchId) }

    suspend fun getConfClassDesc(userId: String, clsId: String) = getResult { apiService.getConfClassDesc(userId, clsId) }
    suspend fun getLiveClassDesc(userId: String, lcsId: String) = getResult { apiService.getLiveClassDesc(userId, lcsId) }
    suspend fun getJoinLiveClass(lcsId: String) = getResult { apiService.getJoinLiveClass(lcsId) }
    suspend fun getConfPreviousClasses(clsId: String) = getResult { apiService.getConfPreviousClasses(clsId) }
    suspend fun getLivePreviousClasses(lcsId: String) = getResult { apiService.getLivePreviousClasses(lcsId) }
    suspend fun getConfComments(pcId: String) = getResult { apiService.getConfComments(pcId) }
    suspend fun postConfComment(userId: Int, pcId: Int, comment: String) = getResult {
        apiService.postConfComment(userId, pcId, comment)
    }

    suspend fun getLiveComments(lcId: String) = getResult { apiService.getLiveComments(lcId) }
    suspend fun postLiveComment(userId: Int, lcId: Int, comment: String) = getResult {
        apiService.postLiveComment(userId, lcId, comment)
    }

    suspend fun getEnrolledBatch(userId: String, batchId: String) =
        getResult { apiService.getEnrolledBatch(userId, batchId) }


    //Test Series
    suspend fun getTestSeries() = getResult { apiService.getTestSeries() }
    suspend fun getTestSeriesDetails(userId: String, testId: String) =
        getResult { apiService.getTestSeriesDetails(userId, testId) }

    suspend fun getListTests(testId: String) = getResult { apiService.getListTests(testId) }
    suspend fun getStartTest(userId: String, testId: String) = getResult { apiService.getStartTest(userId, testId) }
    suspend fun submitTest(endTest: EndTest) = getResult { apiService.submitTest(endTest) }
    suspend fun getEnrolled(userId: String, tsId: String) =
        getResult { apiService.getEnrolled(userId, tsId) }

    //Practice Sets
    suspend fun getSetSeries() = getResult { apiService.getSetSeries() }
    suspend fun getSetSeriesDetails(userId: String, pssId: String) =
        getResult { apiService.getSetSeriesDetails(userId, pssId) }

    suspend fun getListSets(pssId: String) = getResult { apiService.getListSets(pssId) }
    suspend fun getStartSet(userId: String, psId: String) = getResult { apiService.getStartSet(userId, psId) }

    //    suspend fun submitTest(endTest: EndTest) = getResult { apiService.submitTest(endTest) }
    suspend fun getEnrolledSet(userId: String, pssId: String) =
        getResult { apiService.getEnrolledSet(userId, pssId) }


    //    PDF Tests
    suspend fun getPDF() = getResult { apiService.getPDF() }
    suspend fun getPDFDetails(ptsId: String) = getResult { apiService.getPDFDetails(ptsId) }

    //Blogs
    suspend fun getBlogs() = getResult { apiService.getBlogs() }
    suspend fun getComments(blogId: String) = getResult { apiService.getComments(blogId) }
    suspend fun postComment(userId: Int, blogId: Int, comment: String) = getResult {
        apiService.postComment(userId, blogId, comment)
    }

    suspend fun getDiscussionForumDetail(dId: String) = getResult { apiService.getDiscussionForumDetail(dId) }
    suspend fun getDiscussionComments(dId: String) = getResult { apiService.getDiscussionComments(dId) }
    suspend fun postDiscussionComment(userId: Int, dId: Int, comment: String) = getResult {
        apiService.postDiscussionComment(userId, dId, comment)
    }

    suspend fun filterBlogs(cId: String, scId: String) = getResult {
        apiService.filterBlogs(cId, scId)
    }

    //    others
    suspend fun getGallery() = getResult { apiService.getGallery() }

    suspend fun getCategory() = getResult { apiService.getCategory() }
    suspend fun getSubCategory(cid: String) =
        getResult { apiService.getSubCategory(cid) }

    //    notes
    suspend fun getLibraryNotes() = getResult { apiService.getLibraryNotes() }
    suspend fun getSampleNotes() = getResult { apiService.getSampleNotes() }
    suspend fun getCaNotes() = getResult { apiService.getCaNotes() }
    suspend fun getNcertNotes() = getResult { apiService.getNcertNotes() }
    suspend fun getAllNotes() = getResult { apiService.getAllNotes() }
    suspend fun filterNotes(cId: String, scId: String) = getResult {
        apiService.filterNotes(cId, scId)
    }


    //    Payment
    suspend fun getPackages() = getResult { apiService.getPackages() }
    suspend fun getCoupons(userId: String) = getResult { apiService.getCoupons(userId) }
    suspend fun getPaymentDataPackage(userId: String, pckId: String, coupon: String? = null) =
        getResult { apiService.getPaymentDataPackage(userId, pckId, coupon) }

    suspend fun getPaymentDataSeries(userId: String, tsId: String) =
        getResult { apiService.getPaymentDataSeries(userId, tsId) }

    suspend fun getPaymentDataPractice(userId: String, pssId: String) =
        getResult { apiService.getPaymentDataPractice(userId, pssId) }


    suspend fun getPaymentDataCourse(userId: String, cId: String) =
        getResult { apiService.getPaymentDataCourse(userId, cId) }

    suspend fun getPaymentDataBatch(userId: String, batchId: String) =
        getResult { apiService.getPaymentDataBatch(userId, batchId) }

    suspend fun purchasePackage(
        userId: String,
        orderId: String,
        paymentId: String
    ) = getResult { apiService.purchasePackage(userId, orderId, paymentId) }

    suspend fun purchaseSeries(
        userId: String,
        orderId: String,
        paymentId: String
    ) = getResult { apiService.purchaseSeries(userId, orderId, paymentId) }

    suspend fun purchasePractice(
        userId: String,
        orderId: String,
        paymentId: String
    ) = getResult { apiService.purchasePractice(userId, orderId, paymentId) }

    suspend fun purchaseCourse(
        userId: String,
        orderId: String,
        paymentId: String
    ) = getResult { apiService.purchaseCourse(userId, orderId, paymentId) }

    suspend fun purchaseBatch(
        userId: String,
        orderId: String,
        paymentId: String
    ) = getResult { apiService.purchaseBatch(userId, orderId, paymentId) }

    suspend fun login(username: String, password: String) = getResult { apiService.login(username, password) }
    suspend fun register(
        name: String,
        email: String,
        contact: String,
        password: String
    ) = getResult { apiService.register(name, email, contact, password) }

    //Courses
    suspend fun getCourses() = getResult { apiService.getCourses() }
    suspend fun getCourseDetails(userId: String, cid: String) =
        getResult { apiService.getCourseDetails(userId, cid) }

    suspend fun getEnrolledCourse(userId: String, cId: String) =
        getResult { apiService.getEnrolledCourse(userId, cId) }

    suspend fun getPostalCourses() = getResult { apiService.getPostalCourses() }
    suspend fun submitPostalAddress(submitPostalAddress: SubmitPostalAddress) =
        getResult { apiService.submitPostalAddress(submitPostalAddress) }

    suspend fun getPostalOrders(userId: String) = getResult { apiService.getPostalOrders(userId) }
}