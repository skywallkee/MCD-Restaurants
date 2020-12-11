package masterchefdevs.colectiv.ubb.chefs.data

import android.content.ContentValues
import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import  masterchefdevs.colectiv.ubb.chefs.core.Result
import masterchefdevs.colectiv.ubb.chefs.core.TAG
import masterchefdevs.colectiv.ubb.chefs.data.model.Restaurant
import masterchefdevs.colectiv.ubb.chefs.data.remote.RemoteRestaurantDataSource

class RestaurantViewModel  : ViewModel() {
    private val mutableRestaurant = MutableLiveData<Restaurant>()
    val restaurant: LiveData<Restaurant> = mutableRestaurant

    fun getRestaurant(restaurantId: Number) {
        Log.d(TAG, "inside getRestaurant_view model")
        viewModelScope.launch {
            Log.v(ContentValues.TAG, "getRestaurant")
            val result = RemoteRestaurantDataSource.getRestaurant(restaurantId)
            if (result is Result.Success<Restaurant>)
                mutableRestaurant.value = result.data;
        }
    }

    fun makeReservation(username: String, password1: String, password2: String) {

//        viewModelScope.launch {
//            Log.v(ContentValues.TAG, "register...");
//            mutableLoginResult.value = LoginRepository.register(username, password1, password2)
//        }
    }

}