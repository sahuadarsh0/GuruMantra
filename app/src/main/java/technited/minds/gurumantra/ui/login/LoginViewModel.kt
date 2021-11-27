package technited.minds.gurumantra.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import technited.minds.gurumantra.data.repository.MainRepository
import technited.minds.gurumantra.model.LoginDetails
import technited.minds.gurumantra.model.RegisterDetails
import technited.minds.gurumantra.utils.Resource
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {

    val loginDetails: MutableLiveData<Resource<LoginDetails>> = MutableLiveData()
    val registerDetails: MutableLiveData<Resource<RegisterDetails>> = MutableLiveData()

    fun login(email: String, password: String) = viewModelScope.launch {
        loginDetails.postValue(repository.login(email, password))
    }

    fun register(name: String, email: String, contact: String, password: String) = viewModelScope.launch {
        registerDetails.postValue(repository.register(name, email, contact, password))
    }

}