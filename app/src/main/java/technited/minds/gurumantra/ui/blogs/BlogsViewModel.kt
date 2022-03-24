package technited.minds.gurumantra.ui.blogs

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import technited.minds.gurumantra.data.repository.MainRepository
import technited.minds.gurumantra.model.*
import technited.minds.gurumantra.utils.Resource
import javax.inject.Inject

@HiltViewModel
class BlogsViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {


    val blogs: MutableLiveData<Resource<GetBlogs>> = MutableLiveData()
    val dcs: MutableLiveData<Resource<GetDcs>> = MutableLiveData()

    fun getBlogs() = viewModelScope.launch {
        blogs.postValue(repository.getBlogs())
    }

    fun getDiscussionForumDetail(dId: String) = viewModelScope.launch {
        dcs.postValue(repository.getDiscussionForumDetail(dId))
    }

    fun filterBlogs(cId: String, scId: String) = viewModelScope.launch {
        blogs.postValue(repository.filterBlogs(cId, scId))
    }
}