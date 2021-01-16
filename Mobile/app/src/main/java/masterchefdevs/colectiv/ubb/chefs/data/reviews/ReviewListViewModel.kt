package masterchefdevs.colectiv.ubb.chefs.data.reviews

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import masterchefdevs.colectiv.ubb.chefs.core.TAG
import masterchefdevs.colectiv.ubb.chefs.data.RestaurantRepository


import masterchefdevs.colectiv.ubb.chefs.data.model.Restaurant
import masterchefdevs.colectiv.ubb.chefs.data.model.Review

class ReviewListViewModel : ViewModel() {


    private val mutableLoading = MutableLiveData<Boolean>().apply { value = false }
    private val mutableException = MutableLiveData<Exception>().apply { value = null }

    private val mutableItems = MutableLiveData<List<Review>>().apply { value = emptyList() }


    val items: LiveData<List<Review>> = mutableItems
    val loading: LiveData<Boolean> = mutableLoading
    val loadingError: LiveData<Exception> = mutableException


    fun loadItems() {

        viewModelScope.launch {
            Log.v(TAG, "loadItems...");
            mutableLoading.value = true
            mutableException.value = null
            try {
                mutableItems.value = ReviewRepository.loadAll()
                Log.d(TAG, "loadItems succeeded");
                mutableLoading.value = false
            } catch (e: Exception) {
                Log.w(TAG, "loadItems failed", e);
                mutableException.value = e
                mutableLoading.value = false
            }
        }
    }
}