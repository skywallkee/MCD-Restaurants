package masterchefdevs.colectiv.ubb.chefs.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import masterchefdevs.colectiv.ubb.chefs.core.Result
import masterchefdevs.colectiv.ubb.chefs.core.TAG
import masterchefdevs.colectiv.ubb.chefs.data.model.Reservation
import masterchefdevs.colectiv.ubb.chefs.data.model.Restaurant
import masterchefdevs.colectiv.ubb.chefs.data.model.Table
import masterchefdevs.colectiv.ubb.chefs.data.model.Wall
import masterchefdevs.colectiv.ubb.chefs.data.remote.RemoteRestaurantDataSource
import java.util.Calendar
import java.util.Date

class RestaurantViewModel  : ViewModel() {
    private val mutableRestaurant = MutableLiveData<Restaurant>()
    private val mutableTables = MutableLiveData<List<Table>>()
    private val mutableWalls = MutableLiveData<List<Wall>>()
    private val mutableReservations = MutableLiveData<List<Reservation>>()

    val restaurant: LiveData<Restaurant> = mutableRestaurant

    val tables: LiveData<List<Table>> = mutableTables
    val walls: LiveData<List<Wall>> = mutableWalls
    val reservations: LiveData<List<Reservation>> = mutableReservations

       fun getRestaurant(restaurantId: Number) {
        Log.d(TAG, "inside getRestaurant_view model")
        viewModelScope.launch {
            val result = RemoteRestaurantDataSource.getRestaurant(restaurantId)
            if (result is Result.Success<Restaurant>)
                mutableRestaurant.value = result.data;
        }
    }

    fun isReserved(res: Reservation, date: Date, duration: Int): Boolean {
        Log.d(TAG, date.time.toString())
        Log.d(TAG, res.data.toString())
        Log.d(TAG, "___")

        //pentru data de la new res
        val calendar: Calendar = Calendar.getInstance()
        calendar.set(
            date.year, date.month, date.day,
            date.hours, date.minutes, 0 )
        val startTime: Long = calendar.getTimeInMillis()

        //pentru data de la old res
        val calendar1: Calendar = Calendar.getInstance()
        calendar.set(
            res.data.year, res.data.month, res.data.day,
            res.data.hours, res.data.minutes, 0 )
        val startTime1: Long = calendar1.getTimeInMillis()
        val startTime2: Long = (duration*3600000).toLong()
        if (startTime>=startTime1 && startTime<=startTime2)
            return true
        return false
    }
    fun getMeseRestaurant(restaurantId: Number, date: Date) {
        Log.d(TAG, "inside getMese_view model")
        viewModelScope.launch {
            val result = RemoteRestaurantDataSource.getMese(restaurantId)
            Log.d(TAG,date.toString())
            val reservationsResult = RemoteRestaurantDataSource.getRezervari(restaurantId, date)
            if (result is Result.Success<List<Table>> && (reservationsResult is Result.Success<List<Reservation>>)) {
                result.data.forEach { table ->
                    var myRes = reservationsResult.data.filter { reservation ->
                        if (reservation.id_M == table.id)
                            return@filter isReserved(reservation, date, 0)
                        return@filter true
                    }
                    table.reserved = myRes.size > 0
                }
                mutableTables.value = result.data;
            }
        }
    }

    fun getPeretiRestaurant(restaurantId: Number) {
        Log.d(TAG, "inside getPereti_view model")
        viewModelScope.launch {
            val result = RemoteRestaurantDataSource.getPereti(restaurantId)
            if (result is Result.Success<List<Wall>>)
                mutableWalls.value = result.data;
        }
    }

    fun getReservations(restaurantId: Number, date: Date){
        Log.d(TAG, "inside get reservations")
        viewModelScope.launch {
            val result = RemoteRestaurantDataSource.getRezervari(restaurantId, date)
            if (result is Result.Success<List<Reservation>>)
                mutableReservations.value = result.data
        }
    }

    fun makeReservation(username: String, password1: String, password2: String) {

//        viewModelScope.launch {
//            Log.v(ContentValues.TAG, "register...");
//            mutableLoginResult.value = LoginRepository.register(username, password1, password2)
//        }
    }

}