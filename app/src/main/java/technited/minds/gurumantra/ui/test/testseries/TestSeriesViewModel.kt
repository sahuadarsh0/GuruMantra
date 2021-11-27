package technited.minds.gurumantra.ui.test.testseries

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import technited.minds.gurumantra.data.repository.MainRepository
import technited.minds.gurumantra.model.*
import technited.minds.gurumantra.utils.Resource
import javax.inject.Inject


@HiltViewModel
class TestSeriesViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {

    val testSeries = liveData { emit(repository.getTestSeries()) }

    val tests: MutableLiveData<Resource<ListTests>> = MutableLiveData()
    val testSeriesDetails: MutableLiveData<Resource<TestDetails>> = MutableLiveData()
    val enroll: MutableLiveData<Resource<Enrolled>> = MutableLiveData()

    fun getListTests(tsId: String) = viewModelScope.launch {
        tests.postValue(repository.getListTests(tsId))
    }

    fun getTestSeriesDetails(tsId: String, uId: String) = viewModelScope.launch {
        testSeriesDetails.postValue(repository.getTestSeriesDetails(tsId, uId))
    }

    fun getEnrolled(tsId: String, userId: String) = viewModelScope.launch {
        enroll.postValue(repository.getEnrolled(tsId, userId))
    }
}