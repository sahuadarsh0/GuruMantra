package technited.minds.gurumantra.ui.blogs

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import technited.minds.gurumantra.data.repository.MainRepository
import technited.minds.gurumantra.model.*
import technited.minds.gurumantra.utils.Resource
import javax.inject.Inject

@HiltViewModel
class CommentsViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {


    val comment: MutableLiveData<Resource<GetComment>> = MutableLiveData()
    val response: MutableLiveData<Resource<CommentResponse>> = MutableLiveData()

    fun getComments(id: String,type: String) = viewModelScope.launch {
        comment.postValue(repository.getComments(id,type))
    }

    fun postComment(userId: Int, id: Int, comment: String,type: String) = viewModelScope.launch {
        response.postValue(repository.postComment(userId, id, comment,type))
    }

}