package technited.minds.gurumantra.ui.blogs

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import technited.minds.gurumantra.data.repository.MainRepository
import technited.minds.gurumantra.model.Comment
import technited.minds.gurumantra.utils.Resource
import javax.inject.Inject

@HiltViewModel
class BlogsViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {

    val blogs =  repository.getBlogs()

    val comment: MutableLiveData<Resource<List<Comment>>> = MutableLiveData()

    fun getComments(blogId: String) = viewModelScope.launch {
        comment.postValue(repository.getComments(blogId))
    }
}