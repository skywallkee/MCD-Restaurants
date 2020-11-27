package masterchefdevs.colectiv.ubb.chefs.data

import android.content.ContentValues
import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import masterchefdevs.colectiv.ubb.chefs.R
import masterchefdevs.colectiv.ubb.chefs.auth.data.LoginRepository
import masterchefdevs.colectiv.ubb.chefs.auth.login.LoginFormState
import masterchefdevs.colectiv.ubb.chefs.data.model.Restaurant

class RestaurantViewModel  : ViewModel() {
    private val mutableRestaurant = MutableLiveData<Restaurant>()
    val restaurant: LiveData<Restaurant> = mutableRestaurant

    fun getRestaurant(restaurantId: String) {
        Log.v(ContentValues.TAG, "inside restaurant view model")
//        viewModelScope.launch {
//            Log.v(ContentValues.TAG, "login...");
//            mutableLoginResult.value = LoginRepository.login(username, password)
//        }
    }
    fun makeReservation(username: String, password1: String, password2: String) {

//        viewModelScope.launch {
//            Log.v(ContentValues.TAG, "register...");
//            mutableLoginResult.value = LoginRepository.register(username, password1, password2)
//        }
    }

}