package technited.minds.gurumantra.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import technited.minds.gurumantra.data.repository.MainRepository
import technited.minds.gurumantra.model.FetchMeeting
import technited.minds.gurumantra.model.JoinLiveClass
import technited.minds.gurumantra.model.LoginDetails
import technited.minds.gurumantra.utils.Resource
import javax.inject.Inject

@HiltViewModel
class LiveViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {

    val meeting: MutableLiveData<Resource<FetchMeeting>> = MutableLiveData()
    val joinLive: MutableLiveData<Resource<JoinLiveClass>> = MutableLiveData()

//    fun fetchMeeting(classNo: String) = viewModelScope.launch {
//        meeting.postValue(repository.getFetchMeeting(classNo))
//    }

    fun fetchMeeting(userId: String, id: String,type: Int) = viewModelScope.launch {
        meeting.postValue(repository.getClassDescription(userId,id,type))
    }

    fun getJoinLiveClass(lcsId: String) = viewModelScope.launch {
        joinLive.postValue(repository.getJoinLiveClass(lcsId))
    }

}