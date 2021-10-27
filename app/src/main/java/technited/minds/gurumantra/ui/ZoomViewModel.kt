package technited.minds.gurumantra.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import technited.minds.gurumantra.data.repository.MainRepository
import technited.minds.gurumantra.model.FetchMeeting
import technited.minds.gurumantra.model.LoginDetails
import technited.minds.gurumantra.utils.Resource
import javax.inject.Inject

@HiltViewModel
class ZoomViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {

    val meeting: MutableLiveData<Resource<FetchMeeting>> = MutableLiveData()

    fun fetchMeeting(classNo: String) = viewModelScope.launch {
        meeting.postValue(repository.getFetchMeeting(classNo))
    }

}