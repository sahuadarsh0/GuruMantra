package technited.minds.gurumantra.ui.packages

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import dagger.hilt.android.lifecycle.HiltViewModel
import technited.minds.gurumantra.data.repository.MainRepository
import javax.inject.Inject

@HiltViewModel
class PackagesViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {


    val packages = liveData { emit(repository.getPackages()) }
}