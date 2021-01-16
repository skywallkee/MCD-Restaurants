package masterchefdevs.colectiv.ubb.chefs.data.reviews

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import masterchefdevs.colectiv.ubb.chefs.core.Result
import masterchefdevs.colectiv.ubb.chefs.core.TAG
import masterchefdevs.colectiv.ubb.chefs.data.RestaurantRepository
import masterchefdevs.colectiv.ubb.chefs.data.model.Restaurant
import masterchefdevs.colectiv.ubb.chefs.data.model.Review
import masterchefdevs.colectiv.ubb.chefs.data.remote.RemoteRestaurantDataSource

class ReviewViewModel  : ViewModel() {
    private val mutableItem = MutableLiveData<Review>().apply { value = Review(0, 0,"","",0,
        1
    ) }
    private val mutableFetching = MutableLiveData<Boolean>().apply { value = false }
    private val mutableCompleted = MutableLiveData<Boolean>().apply { value = false }
    private val mutableException = MutableLiveData<Exception>().apply { value = null }
    private val mutableRating = MutableLiveData<Float>()

    var rating: LiveData<Float> = mutableRating
    val item: LiveData<Review> = mutableItem
    val fetching: LiveData<Boolean> = mutableFetching
    val fetchingError: LiveData<Exception> = mutableException
    val completed: LiveData<Boolean> = mutableCompleted

    fun loadItem(itemId: String) {
        viewModelScope.launch {
            Log.i(TAG, "loadItem...")
            mutableFetching.value = true
            mutableException.value = null
            try {
                mutableItem.value = ReviewRepository.load(itemId.toInt())
                Log.i(TAG, "loadItem succeeded")
                val rat =  RemoteRestaurantDataSource.getRating(itemId.toInt())
                if (rat is Result.Success<Float>) {
                    mutableRating.value = rat.data
                }
                mutableFetching.value = false
            } catch (e: Exception) {
                Log.w(TAG, "loadItem failed", e)
                mutableException.value = e
                mutableFetching.value = false
            }
        }
    }

//    fun saveOrUpdateItem(nameR: String,adresa:String,latime:Number,lungime:Number) {
//        viewModelScope.launch {
//            Log.i(TAG, "saveOrUpdateItem...");
//            val item = mutableItem.value ?: return@launch
//            item.adresa=adresa
//            item.nameR=nameR
//            item.latime=latime
//            item.lungime=lungime
//
//
//            mutableFetching.value = true
//            mutableException.value = null
//            try {
//                if (item.id.toString().isNotEmpty()) {
//                    mutableItem.value = RestaurantRepository.update(item)
//                } else {
//                    mutableItem.value = RestaurantRepository.save(item)
//                }
//                Log.i(TAG, "saveOrUpdateItem succeeded");
//                mutableCompleted.value = true
//                mutableFetching.value = false
//            } catch (e: Exception) {
//                Log.w(TAG, "saveOrUpdateItem failed", e);
//                mutableException.value = e
//                mutableFetching.value = false
//            }
//        }
//    }
}