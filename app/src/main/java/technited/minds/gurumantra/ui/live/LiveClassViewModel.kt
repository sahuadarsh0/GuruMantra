package technited.minds.gurumantra.ui.live

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import technited.minds.gurumantra.data.repository.MainRepository
import technited.minds.gurumantra.model.Batches
import technited.minds.gurumantra.model.PreviousClasses
import technited.minds.gurumantra.utils.Resource
import javax.inject.Inject

@HiltViewModel
class LiveClassViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {


    val batches: MutableLiveData<Resource<Batches>> = MutableLiveData()

    fun getBatches(type: String) = viewModelScope.launch {
        batches.postValue(repository.getBatches(type))
    }

    val previousClasses: MutableLiveData<Resource<PreviousClasses>> = MutableLiveData()

    fun getPreviousClasses(id: String, type: Int) = viewModelScope.launch {
        previousClasses.postValue(repository.getPreviousClasses(id, type))
    }
}