package masterchefdevs.colectiv.ubb.chefs.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import  masterchefdevs.colectiv.ubb.chefs.core.Result
import masterchefdevs.colectiv.ubb.chefs.core.TAG
import masterchefdevs.colectiv.ubb.chefs.data.model.Restaurant
import masterchefdevs.colectiv.ubb.chefs.data.model.Table
import masterchefdevs.colectiv.ubb.chefs.data.model.Wall
import masterchefdevs.colectiv.ubb.chefs.data.remote.RemoteRestaurantDataSource

class RestaurantViewModel  : ViewModel() {
    private val mutableRestaurant = MutableLiveData<Restaurant>()
    private val mutableTables = MutableLiveData<List<Table>>()
    private val mutableWalls = MutableLiveData<List<Wall>>()

    val restaurant: LiveData<Restaurant> = mutableRestaurant

    val tables: LiveData<List<Table>> = mutableTables
    val walls: LiveData<List<Wall>> = mutableWalls

       fun getRestaurant(restaurantId: Number) {
        Log.d(TAG, "inside getRestaurant_view model")
        viewModelScope.launch {
            val result = RemoteRestaurantDataSource.getRestaurant(restaurantId)
            if (result is Result.Success<Restaurant>)
                mutableRestaurant.value = result.data;
        }
    }

    fun getMeseRestaurant(restaurantId: Number) {
        Log.d(TAG, "inside getMese_view model")
        viewModelScope.launch {
            val result = RemoteRestaurantDataSource.getMese(restaurantId)
            if (result is Result.Success<List<Table>>)
                mutableTables.value = result.data;
        }
    }

    fun getPeretiRestaurant(restaurantId: Number) {
        Log.d(TAG, "inside getMese_view model")
        viewModelScope.launch {
            val result = RemoteRestaurantDataSource.getPereti(restaurantId)
            if (result is Result.Success<List<Wall>>)
                mutableWalls.value = result.data;
        }
    }


    fun makeReservation(username: String, password1: String, password2: String) {

//        viewModelScope.launch {
//            Log.v(ContentValues.TAG, "register...");
//            mutableLoginResult.value = LoginRepository.register(username, password1, password2)
//        }
    }

}