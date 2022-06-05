package technited.minds.gurumantra.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import technited.minds.gurumantra.data.repository.MainRepository
import technited.minds.gurumantra.model.GetSubCategory
import technited.minds.gurumantra.utils.Resource
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {

    val category = liveData { emit(repository.getCategory()) }
    val subCategory: MutableLiveData<Resource<GetSubCategory>> = MutableLiveData()

    fun getSubCategory(cid: String) = viewModelScope.launch {
        subCategory.postValue(repository.getSubCategory(cid))
    }
}