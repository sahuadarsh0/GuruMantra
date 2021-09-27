package technited.minds.gurumantra.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import technited.minds.gurumantra.data.repository.MainRepository
import technited.minds.gurumantra.model.LoginDetails
import technited.minds.gurumantra.utils.Resource
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {

    val details: MutableLiveData<Resource<LoginDetails>> = MutableLiveData()

    fun login(username: String, password: String) = viewModelScope.launch {
        repository.login(username, password)
        details.postValue(repository.login(username, password))
    }

}