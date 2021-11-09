package technited.minds.gurumantra.ui.test

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Response
import technited.minds.gurumantra.data.repository.MainRepository
import technited.minds.gurumantra.model.EndTest
import technited.minds.gurumantra.model.Result
import technited.minds.gurumantra.model.StartTest
import technited.minds.gurumantra.utils.Resource
import javax.inject.Inject


@HiltViewModel
class ExamViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {

    val testResultUrl: MutableLiveData<Resource<Result>> = MutableLiveData()
    val testStart: MutableLiveData<Resource<StartTest>> = MutableLiveData()

    fun getStartTest(tId: String, uId: String) = viewModelScope.launch {
        testStart.postValue(repository.getStartTest(tId, uId))
    }

    fun submitTest(endTest: EndTest) = viewModelScope.launch {
        testResultUrl.postValue(repository.submitTest(endTest))
    }
}