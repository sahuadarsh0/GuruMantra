package technited.minds.gurumantra.ui.test.practice

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import technited.minds.gurumantra.data.repository.MainRepository
import technited.minds.gurumantra.model.BatchDetailsItem
import technited.minds.gurumantra.model.ListTests
import technited.minds.gurumantra.model.MeetingDetails
import technited.minds.gurumantra.model.TestDetails
import technited.minds.gurumantra.utils.Resource
import javax.inject.Inject


@HiltViewModel
class PracticeSetViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {

    val setSeries = liveData { emit(repository.getSetSeries()) }

//    val tests: MutableLiveData<Resource<ListTests>> = MutableLiveData()
//    val testSeriesDetails: MutableLiveData<Resource<TestDetails>> = MutableLiveData()

//    fun getListTests(tsId: String) = viewModelScope.launch {
//        tests.postValue(repository.getListTests(tsId))
//    }

//    fun getTestSeriesDetails(tsId: String, uId: String) = viewModelScope.launch {
//        testSeriesDetails.postValue(repository.getTestSeriesDetails(tsId, uId))
//    }
}