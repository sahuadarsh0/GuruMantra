package technited.minds.gurumantra.data.remote

import technited.minds.gurumantra.model.EndTest
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiService: ApiService) : BaseDataSource() {
    //    Home
    suspend fun getMeetings(batchNo: String) = getResult { apiService.getMeetings(batchNo) }
    suspend fun getBatchDetails(batchNo: String) = getResult { apiService.getBatchDetails(batchNo) }
    suspend fun getFetchMeeting(classNo: String) = getResult { apiService.getFetchMeeting(classNo) }
    suspend fun getBatches() = getResult { apiService.getBatches() }


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

    //    others
    suspend fun getGallery() = getResult { apiService.getGallery() }


    //    Payment
    suspend fun getPackages() = getResult { apiService.getPackages() }
    suspend fun getPaymentDataPackage(userId: String, pckId: String) =
        getResult { apiService.getPaymentDataPackage(userId, pckId) }

    suspend fun getPaymentDataSeries(userId: String, tsId: String) =
        getResult { apiService.getPaymentDataSeries(userId, tsId) }

    suspend fun getPaymentDataPractice(userId: String, pssId: String) =
        getResult { apiService.getPaymentDataPractice(userId, pssId) }

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

    suspend fun login(username: String, password: String) = getResult { apiService.login(username, password) }
    suspend fun register(
        name: String,
        email: String,
        contact: String,
        password: String
    ) = getResult { apiService.register(name, email, contact, password) }
}