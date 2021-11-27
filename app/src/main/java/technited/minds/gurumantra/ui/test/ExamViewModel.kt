package technited.minds.gurumantra.ui.test

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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

    fun getStartTest(uId: String, tId: String, type: String) = viewModelScope.launch {
        testStart.postValue(repository.getStartTest(uId, tId, type))
    }

    fun submitTest(endTest: EndTest) = viewModelScope.launch {
        testResultUrl.postValue(repository.submitTest(endTest))
    }
}