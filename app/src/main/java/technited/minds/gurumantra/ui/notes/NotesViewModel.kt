package technited.minds.gurumantra.ui.notes

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import technited.minds.gurumantra.data.repository.MainRepository
import technited.minds.gurumantra.model.Comment
import technited.minds.gurumantra.model.CommentResponse
import technited.minds.gurumantra.utils.Resource
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {


    val notes = liveData { emit(repository.getNotes()) }

}