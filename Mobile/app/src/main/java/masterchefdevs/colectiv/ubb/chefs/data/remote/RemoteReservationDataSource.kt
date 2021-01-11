package masterchefdevs.colectiv.ubb.chefs.data.remote

import masterchefdevs.colectiv.ubb.chefs.core.Api
import masterchefdevs.colectiv.ubb.chefs.core.Result
import masterchefdevs.colectiv.ubb.chefs.core.TokenInterceptor
import masterchefdevs.colectiv.ubb.chefs.data.model.Reservation
import masterchefdevs.colectiv.ubb.chefs.data.model.ReservationDTO

import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import java.util.*

object RemoteReservationDataSource {
    interface ReservationService {
        @Headers("Content-Type: application/json")
        @POST("/api/rezervari/")
        suspend fun reserve(@Body reservation: ReservationDTO): Reservation
    }

    private val reservationService: ReservationService = Api.retrofit.create(
        ReservationService::class.java)

    suspend fun reserve(reservation: ReservationDTO): Result<Reservation>{
        try {
            val a = reservationService.reserve(reservation)
            return Result.Success(a)
        } catch (e: Exception) {
            return Result.Error(e)
        }
    }
}