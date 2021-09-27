package technited.minds.gurumantra.data.remote

import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiService: ApiService) : BaseDataSource() {
//    suspend fun getMembersList() = getResult { apiService.getMembersList() }
//    suspend fun getMyMembersList(memberId: String) = getResult { apiService.getMyMembersList(memberId) }
    suspend fun getMeetings() = getResult { apiService.getMeetings() }
    suspend fun getBatches() = getResult { apiService.getBatches() }
//    suspend fun getMemberProfile(memberId: String) = getResult { apiService.getMemberProfile(memberId) }
    suspend fun login(username: String, password: String) = getResult { apiService.login(username, password) }
}