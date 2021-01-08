package masterchefdevs.colectiv.ubb.chefs.data.remote

import com.google.gson.JsonObject
import masterchefdevs.colectiv.ubb.chefs.core.Api
import masterchefdevs.colectiv.ubb.chefs.data.model.DayStatDTO
import masterchefdevs.colectiv.ubb.chefs.data.model.Reservation
import masterchefdevs.colectiv.ubb.chefs.core.Result
import masterchefdevs.colectiv.ubb.chefs.data.model.IdDto

import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

object RemoteReservationDataSource {
    interface ReservationService {
        @Headers("Content-Type: application/json")
        @POST("/api/rezervari/")
        suspend fun reserve(@Body reservation: Reservation): Int
    }

    private val reservationService: RemoteReservationDataSource.ReservationService = Api.retrofit.create(
        RemoteReservationDataSource.ReservationService::class.java)

    suspend fun reserve(reservation: Reservation): Result<Int>{
        try {
            val a = RemoteReservationDataSource.reservationService.reserve(reservation)
            return Result.Success(a)
        } catch (e: Exception) {
            return Result.Error(e)
        }
    }
}