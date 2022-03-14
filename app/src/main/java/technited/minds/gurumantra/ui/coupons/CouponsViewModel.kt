package technited.minds.gurumantra.ui.coupons

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import technited.minds.gurumantra.data.repository.MainRepository
import technited.minds.gurumantra.model.Coupons
import technited.minds.gurumantra.utils.Resource
import javax.inject.Inject

@HiltViewModel
class CouponsViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {


    val coupons: MutableLiveData<Resource<Coupons>> = MutableLiveData()

    fun getCoupons(userId: String) = viewModelScope.launch {
        coupons.postValue(repository.getCoupons(userId))
    }
}