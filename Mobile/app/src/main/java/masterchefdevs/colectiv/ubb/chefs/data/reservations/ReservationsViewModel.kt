package masterchefdevs.colectiv.ubb.chefs.data.reservations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import masterchefdevs.colectiv.ubb.chefs.core.Result
import masterchefdevs.colectiv.ubb.chefs.data.model.ReservationDTO
import masterchefdevs.colectiv.ubb.chefs.data.remote.RemoteReservationDataSource

class ReservationsViewModel : ViewModel(){
    // 0 or null -> no reservation has been requested
    // 1 -> reservation succeeded
    // 2 -> reservation did not succeed
    private val mutableReservationSucceded = MutableLiveData<Int>()
    private val mutableError = MutableLiveData<String>()

    val livedataReservationSucceded: LiveData<Int> = mutableReservationSucceded
    val livedataError: LiveData<String> = mutableError

    fun makeReservation(reqestReservation: ReservationDTO){
        viewModelScope.launch {
            val result = RemoteReservationDataSource.reserve(reqestReservation)
            if (result is Result.Success)
                mutableReservationSucceded.value = 1
            else
                if (result is Result.Error) {
                    mutableReservationSucceded.value = 2
                    mutableError.value = result.toString()
                }
        }
    }
}