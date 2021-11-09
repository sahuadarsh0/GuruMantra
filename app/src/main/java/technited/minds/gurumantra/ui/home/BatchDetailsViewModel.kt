package technited.minds.gurumantra.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import technited.minds.gurumantra.data.repository.MainRepository
import technited.minds.gurumantra.model.BatchDetailsItem
import technited.minds.gurumantra.model.LoginDetails
import technited.minds.gurumantra.model.MeetingDetails
import technited.minds.gurumantra.utils.Resource
import javax.inject.Inject

@HiltViewModel
class BatchDetailsViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {

    val meetings: MutableLiveData<Resource<MeetingDetails>> = MutableLiveData()
    val batchDetails: MutableLiveData<Resource<BatchDetailsItem>> = MutableLiveData()

    fun getMeetings(batchNo: String) = viewModelScope.launch {
        meetings.postValue(repository.getMeetings(batchNo))
    }
    fun getBatchDetails(batchNo: String) = viewModelScope.launch {
        batchDetails.postValue(repository.getBatchDetails(batchNo))
    }

}