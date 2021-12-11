package technited.minds.gurumantra.ui.live

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import dagger.hilt.android.lifecycle.HiltViewModel
import technited.minds.gurumantra.data.repository.MainRepository
import javax.inject.Inject

@HiltViewModel
class LiveClassViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {


    val batches = liveData { emit(repository.getBatches()) }
}