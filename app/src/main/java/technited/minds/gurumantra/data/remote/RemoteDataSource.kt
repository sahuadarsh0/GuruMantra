package technited.minds.gurumantra.data.remote

import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiService: ApiService) : BaseDataSource() {
    suspend fun getMeetings(batchNo: String) = getResult { apiService.getMeetings(batchNo) }
    suspend fun getBatchDetails(batchNo: String) = getResult { apiService.getBatchDetails(batchNo) }
    suspend fun getFetchMeeting(classNo: String) = getResult { apiService.getFetchMeeting(classNo) }
    suspend fun getBatches() = getResult { apiService.getBatches() }

    //Test Series
    suspend fun getTestSeries() = getResult { apiService.getTestSeries() }
    suspend fun getTestSeriesDetails(tsId: String, uId: String) = getResult { apiService.getTestSeriesDetails(tsId, uId) }
    suspend fun getListTests(tsId: String) = getResult { apiService.getListTests(tsId) }

    //    suspend fun getMemberProfile(memberId: String) = getResult { apiService.getMemberProfile(memberId) }
    suspend fun login(username: String, password: String) = getResult { apiService.login(username, password) }
}