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
    suspend fun getTestSeriesDetails(testId: String, userId: String) =
        getResult { apiService.getTestSeriesDetails(testId, userId) }

    suspend fun getListTests(testId: String) = getResult { apiService.getListTests(testId) }
    suspend fun getStartTest(testId: String, userId: String) = getResult { apiService.getStartTest(testId, userId) }
    suspend fun submitTest(endTest: EndTest) = getResult { apiService.submitTest(endTest) }

    //Blogs
    suspend fun getBlogs() = getResult { apiService.getBlogs() }
    suspend fun getComments(blogId: String) = getResult { apiService.getComments(blogId) }
    suspend fun postComment(userId: Int, blogId: Int, comment: String) = getResult {
        apiService.postComment(userId, blogId, comment)
    }

    //    others
    suspend fun getGallery() = getResult { apiService.getGallery() }
    suspend fun getPackages() = getResult { apiService.getPackages() }

    //    suspend fun getMemberProfile(memberId: String) = getResult { apiService.getMemberProfile(memberId) }
    suspend fun login(username: String, password: String) = getResult { apiService.login(username, password) }
}