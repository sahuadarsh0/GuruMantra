package technited.minds.gurumantra.ui.payment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import technited.minds.gurumantra.data.repository.MainRepository
import technited.minds.gurumantra.model.PaymentOrder
import technited.minds.gurumantra.utils.Resource
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {

    val payment: MutableLiveData<Resource<PaymentOrder>> = MutableLiveData()
    val purchase: MutableLiveData<Resource<String>> = MutableLiveData()

    fun getPaymentData(userId: String, id: String, type: String) = viewModelScope.launch {
        payment.postValue(repository.getPaymentData(userId, id, type))
    }

    fun purchase(
        userId: String,
        orderId: String,
        paymentId: String,
        id: String,
        type: String
    ) = viewModelScope.launch {
        purchase.postValue(repository.purchase(userId, orderId, paymentId,id, type))
    }

}