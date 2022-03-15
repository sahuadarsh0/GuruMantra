package technited.minds.gurumantra.ui.notes

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import technited.minds.gurumantra.data.repository.MainRepository
import technited.minds.gurumantra.model.*
import technited.minds.gurumantra.utils.Resource
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {


    val notes: MutableLiveData<Resource<GetNotes>> = MutableLiveData()
    val libraryNotes = liveData { emit(repository.getLibraryNotes()) }

    fun getNotes(type: String) = viewModelScope.launch {
        notes.postValue(repository.getNotes(type))
    }

    fun filterNotes(cId: String, scId: String) = viewModelScope.launch {
        notes.postValue(repository.filterNotes(cId, scId))
    }
}