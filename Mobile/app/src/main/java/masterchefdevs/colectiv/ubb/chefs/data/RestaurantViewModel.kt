package masterchefdevs.colectiv.ubb.chefs.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import masterchefdevs.colectiv.ubb.chefs.data.model.Restaurant

class RestaurantViewModel  : ViewModel() {
    private val mutableRestaurant = MutableLiveData<Restaurant>()
    val restaurant: LiveData<Restaurant> = mutableRestaurant
}