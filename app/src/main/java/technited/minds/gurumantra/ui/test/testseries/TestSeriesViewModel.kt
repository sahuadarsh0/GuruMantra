package technited.minds.gurumantra.ui.test.testseries

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import technited.minds.gurumantra.data.repository.MainRepository
import technited.minds.gurumantra.model.Enrolled
import technited.minds.gurumantra.model.ListTests
import technited.minds.gurumantra.model.TestDetails
import technited.minds.gurumantra.model.TestSeries
import technited.minds.gurumantra.utils.Resource
import javax.inject.Inject


@HiltViewModel
class TestSeriesViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {

    val testSeries: MutableLiveData<Resource<TestSeries>> = MutableLiveData()

    val tests: MutableLiveData<Resource<ListTests>> = MutableLiveData()
    val testSeriesDetails: MutableLiveData<Resource<TestDetails>> = MutableLiveData()
    val enroll: MutableLiveData<Resource<Enrolled>> = MutableLiveData()

    fun getTestSeries(type: String) = viewModelScope.launch {
        testSeries.postValue(repository.getTestSeries(type))
    }

    fun getListTests(tsId: String, type: String) = viewModelScope.launch {
        tests.postValue(repository.getListTests(tsId, type))
    }

    fun getTestSeriesDetails(userId: String, tsId: String, type: String) = viewModelScope.launch {
        testSeriesDetails.postValue(repository.getTestSeriesDetails(userId, tsId, type))
    }

    fun getEnrolled(tsId: String, userId: String) = viewModelScope.launch {
        enroll.postValue(repository.getEnrolled(tsId, userId))
    }
}