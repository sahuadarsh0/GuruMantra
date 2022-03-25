package technited.minds.gurumantra.ui.courses

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import technited.minds.gurumantra.data.repository.MainRepository
import technited.minds.gurumantra.model.*
import technited.minds.gurumantra.model.CourseDetails
import technited.minds.gurumantra.utils.Resource
import javax.inject.Inject

@HiltViewModel
class CoursesViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {


    val courses = liveData { emit(repository.getCourses()) }

    val postalCourses = liveData { emit(repository.getPostalCourses()) }
    val submitPostalResult: MutableLiveData<Resource<PostalResult>> = MutableLiveData()
    val postalOrders: MutableLiveData<Resource<OrderPostalCourses>> = MutableLiveData()

    val courseDetails: MutableLiveData<Resource<CourseDetails>> = MutableLiveData()
    val enroll: MutableLiveData<Resource<Enrolled>> = MutableLiveData()

    fun getCourseDetails(userId: String, cId: String) = viewModelScope.launch {
        courseDetails.postValue(repository.getCourseDetails(userId, cId))
    }

    fun getEnrolled(userId: String, cId: String, type: String) = viewModelScope.launch {
        enroll.postValue(repository.getEnrolled(userId, cId, type))
    }

    fun submitPostalAddress(submitPostalAddress: SubmitPostalAddress) = viewModelScope.launch {
        submitPostalResult.postValue(repository.submitPostalAddress(submitPostalAddress))
    }

    fun getPostalOrders(userId: String) = viewModelScope.launch {
        postalOrders.postValue(repository.getPostalOrders(userId))
    }
}