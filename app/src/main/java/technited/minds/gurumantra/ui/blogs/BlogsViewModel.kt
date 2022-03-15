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
    val comment: MutableLiveData<Resource<List<Comment>>> = MutableLiveData()
    val dcsComment: MutableLiveData<Resource<GetDcsComment>> = MutableLiveData()
    val dcs: MutableLiveData<Resource<GetDcs>> = MutableLiveData()
    val response: MutableLiveData<Resource<CommentResponse>> = MutableLiveData()

    fun getBlogs() = viewModelScope.launch {
        blogs.postValue(repository.getBlogs())
    }
    fun getComments(blogId: String) = viewModelScope.launch {
        comment.postValue(repository.getComments(blogId))
    }

    fun postComment(userId: Int, blogId: Int, comment: String) = viewModelScope.launch {
        response.postValue(repository.postComment(userId, blogId, comment))
    }

    fun getDiscussionComments(dId: String) = viewModelScope.launch {
        dcsComment.postValue(repository.getDiscussionComments(dId))
    }

    fun getDiscussionForumDetail(dId: String) = viewModelScope.launch {
        dcs.postValue(repository.getDiscussionForumDetail(dId))
    }

    fun postDiscussionComment(userId: Int, dId: Int, comment: String) = viewModelScope.launch {
        response.postValue(repository.postDiscussionComment(userId, dId, comment))
    }

    fun filterBlogs(cId: String, scId: String) = viewModelScope.launch {
        blogs.postValue(repository.filterBlogs(cId, scId))
    }
}