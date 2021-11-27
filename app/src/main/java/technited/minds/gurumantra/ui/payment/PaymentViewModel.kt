package technited.minds.gurumantra.ui.payment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import technited.minds.gurumantra.data.repository.MainRepository
import technited.minds.gurumantra.model.LoginDetails
import technited.minds.gurumantra.model.PaymentOrder
import technited.minds.gurumantra.utils.Resource
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {

    val payment: MutableLiveData<Resource<PaymentOrder>> = MutableLiveData()

    fun getPaymentData(userId: String, pckId: String) = viewModelScope.launch {
        payment.postValue(repository.getPaymentData(userId, pckId))
    }

}