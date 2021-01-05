package masterchefdevs.colectiv.ubb.chefs.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import masterchefdevs.colectiv.ubb.chefs.core.Result
import masterchefdevs.colectiv.ubb.chefs.core.TAG
import masterchefdevs.colectiv.ubb.chefs.data.model.*
import masterchefdevs.colectiv.ubb.chefs.data.remote.RemoteRestaurantDataSource
import java.sql.Time
import java.util.Calendar
import java.util.Date

class RestaurantViewModel  : ViewModel() {
    private val mutableRestaurant = MutableLiveData<Restaurant>()
    private val mutableTables = MutableLiveData<List<Table>>()
    private val mutableWalls = MutableLiveData<List<Wall>>()
    private val mutableReservations = MutableLiveData<List<Reservation>>()
    private val mutableDayStat = MutableLiveData<DayStatDTO>()
    private val mutableHourStat = MutableLiveData<List<Int>>()
    private val mutableRating = MutableLiveData<Float>()
    private val mutableTableIdLists = mutableListOf<Int>()
    var lista_id_mese: MutableList<Int>? = mutableListOf()

    val restaurant: LiveData<Restaurant> = mutableRestaurant

    val tables: LiveData<List<Table>> = mutableTables
    val walls: LiveData<List<Wall>> = mutableWalls
    val reservations: LiveData<List<Reservation>> = mutableReservations
    val dayStat: LiveData<DayStatDTO> = mutableDayStat
    val hourStat: LiveData<List<Int>> = mutableHourStat
    var rating: LiveData<Float> = mutableRating

    fun getRestaurant(restaurantId: Number) {
        viewModelScope.launch {
            val result = RemoteRestaurantDataSource.getRestaurant(restaurantId)
            if (result is Result.Success<Restaurant>) {
                mutableRestaurant.value = result.data
                mutableTableIdLists.clear()
                val rat =  RemoteRestaurantDataSource.getRating(restaurantId.toInt())
                if (rat is Result.Success<Float>) {
                    mutableRating.value = rat.data
                }
            }
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
        val startTime1: Long = res.data_conv.getTimeInMillis()
        val startTime2: Long = (duration*3600000).toLong()
        if (startTime>=startTime1 && startTime<=startTime2)
            return true
        return false
    }

    fun setReservedToAllTables(date: Date){
            mutableTables.value?.forEach { table ->
                var myRes = reservations.value?.filter { reservation ->
                    if (reservation.id_M == table.id)
                        return@filter isReserved(reservation, date, 0)
                    return@filter false
                }
                if (myRes == null)
                    myRes = emptyList()
                table.reserved = myRes.size > 0
            }
    }

    fun getMeseRestaurant(restaurantId: Number) {
        Log.d(TAG, "inside getMese_view model")
        viewModelScope.launch {
            val result = RemoteRestaurantDataSource.getMese(restaurantId)
            if (result is Result.Success) {
                mutableTables.value = result.data
                tables.value?.forEach {
                    lista_id_mese?.add(it.id)
                }
                //getReservations(restaurantId, Date())
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

    fun getDayStat(restaurantId: Int) {
        Log.d(TAG, "inside getDaystat_view model")
        viewModelScope.launch {
            val result = RemoteRestaurantDataSource.getDayStat(restaurantId)
            if (result is Result.Success<DayStatDTO>)
                mutableDayStat.value = result.data
        }
    }
    fun getHourStat(restaurantId: Int, dayId: Int) {
        Log.d(TAG, "inside getDaystat_view model")
        viewModelScope.launch {
            val result = RemoteRestaurantDataSource.getDayStatHour(restaurantId,dayId)
           if (result is Result.Success<List<Int>>)
                mutableHourStat.value = result.data
        }
    }

    fun getReservations(restaurantId: Number, date: Date){
        Log.d(TAG, "inside get reservations")
      viewModelScope.launch {
            val result = RemoteRestaurantDataSource.getRezervari(restaurantId, date)
            if (result is Result.Success<List<Reservation>>) {
                Log.d(TAG, result.data.size.toString())
                val allRelevantReservations: List<Reservation> = result.data.filter {lista_id_mese!!.contains(it.id_M)}
                Log.d(TAG, allRelevantReservations.size.toString())
                    allRelevantReservations.forEach { reservation ->
                    var parts = reservation.data.split("-")
                    val year = parts[0].toInt()
                    val month = parts[1].toInt()
                    val day = parts[2].toInt()
                    parts = reservation.ora.split(":")
                    val hours = parts[0].toInt()
                    val min = parts[1].toInt()
                    parts = reservation.timp.split(":")
                    val thours = parts[0].toInt()
                    val tmin = parts[1].toInt()
                    val cal = Calendar.getInstance()
                    cal.set(year, month, day, hours, min)
                    reservation.data_conv = cal
                    val time = Time(thours, tmin, 0)
                    reservation.timp_conv = time
                }
                mutableReservations.value = allRelevantReservations
            }
        }
    }

    fun refresh(restaurantId: Number, calendar: Calendar){


    }
    fun makeReservation(username: String, password1: String, password2: String) {

//        viewModelScope.launch {
//            Log.v(ContentValues.TAG, "register...");
//            mutableLoginResult.value = LoginRepository.register(username, password1, password2)
//        }
    }

}