package technited.minds.gurumantra.ui.test.pdf

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import dagger.hilt.android.lifecycle.HiltViewModel
import technited.minds.gurumantra.data.repository.MainRepository
import javax.inject.Inject


@HiltViewModel
class PDFViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {

    val getPDF = liveData { emit(repository.getPDF()) }

//    val tests: MutableLiveData<Resource<ListTests>> = MutableLiveData()
//    val testSeriesDetails: MutableLiveData<Resource<TestDetails>> = MutableLiveData()

//    fun getListTests(tsId: String) = viewModelScope.launch {
//        tests.postValue(repository.getListTests(tsId))
//    }

//    fun getTestSeriesDetails(tsId: String, uId: String) = viewModelScope.launch {
//        testSeriesDetails.postValue(repository.getTestSeriesDetails(tsId, uId))
//    }
}