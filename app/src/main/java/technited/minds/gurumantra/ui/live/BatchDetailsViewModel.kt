package technited.minds.gurumantra.ui.live

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import technited.minds.gurumantra.data.repository.MainRepository
import technited.minds.gurumantra.model.BatchDetails
import technited.minds.gurumantra.model.Enrolled
import technited.minds.gurumantra.model.MeetingDetails
import technited.minds.gurumantra.model.PreviousClasses
import technited.minds.gurumantra.utils.Resource
import javax.inject.Inject

@HiltViewModel
class BatchDetailsViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {

    val meetings: MutableLiveData<Resource<MeetingDetails>> = MutableLiveData()
    val batchDetails: MutableLiveData<Resource<BatchDetails>> = MutableLiveData()
    val enroll: MutableLiveData<Resource<Enrolled>> = MutableLiveData()

    fun getMeetings(batchId: String, type: Int) = viewModelScope.launch {
        meetings.postValue(repository.getMeetings(batchId,type))
    }
    fun getBatchDetails(userId: String, batchId: String) = viewModelScope.launch {
        batchDetails.postValue(repository.getBatchDetails(userId,batchId))
    }

    fun getEnrolled(userId: String, batchNo: String, type: String) = viewModelScope.launch {
        enroll.postValue(repository.getEnrolled(userId, batchNo, type))
    }
}