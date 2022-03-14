package technited.minds.gurumantra.ui.courses

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import technited.minds.gurumantra.data.repository.MainRepository
import technited.minds.gurumantra.model.CourseDetails
import technited.minds.gurumantra.model.Enrolled
import technited.minds.gurumantra.utils.Resource
import javax.inject.Inject

@HiltViewModel
class CoursesViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {


    val courses = liveData { emit(repository.getCourses()) }

    val courseDetails: MutableLiveData<Resource<CourseDetails>> = MutableLiveData()
    val enroll: MutableLiveData<Resource<Enrolled>> = MutableLiveData()

    fun getCourseDetails(userId: String, cId: String) = viewModelScope.launch {
        courseDetails.postValue(repository.getCourseDetails(userId, cId))
    }

    fun getEnrolled(userId: String, cId: String, type: String) = viewModelScope.launch {
        enroll.postValue(repository.getEnrolled(userId, cId, type))
    }
}