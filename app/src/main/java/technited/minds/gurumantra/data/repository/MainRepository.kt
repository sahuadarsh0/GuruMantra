package technited.minds.gurumantra.data.repository

import technited.minds.gurumantra.data.local.AnswersDao
import technited.minds.gurumantra.data.local.QuestionsDao
import technited.minds.gurumantra.data.remote.RemoteDataSource
import technited.minds.gurumantra.model.EndTest
import technited.minds.gurumantra.model.QuestionsItem
import technited.minds.gurumantra.utils.performGetOperation
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localQuestionsDataSource: QuestionsDao,
    private val localAnswersDataSource: AnswersDao
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


    //Test Series
    suspend fun getTestSeries() = remoteDataSource.getTestSeries()
    suspend fun getTestSeriesDetails(tsId: String, uId: String) = remoteDataSource.getTestSeriesDetails(tsId, uId)
    suspend fun getListTests(tsId: String) = remoteDataSource.getListTests(tsId)
    suspend fun getStartTest(tId: String, uId: String) =  remoteDataSource.getStartTest(tId, uId)
    suspend fun submitTest(endTest: EndTest) =  remoteDataSource.submitTest(endTest)
//    fun getStartTest(tsId: String, uId: String) = performGetOperation(
//        databaseQuery = { localQuestionsDataSource.getQuestionsList() },
//        networkCall = { remoteDataSource.getStartTest(tsId, uId) },
//        saveCallResult = { localQuestionsDataSource.insertAll(it.ques) }
//    )
}