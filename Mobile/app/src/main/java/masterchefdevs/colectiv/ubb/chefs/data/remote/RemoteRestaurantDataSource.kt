package masterchefdevs.colectiv.ubb.chefs.data.remote

import android.util.Log
import masterchefdevs.colectiv.ubb.chefs.core.Result
import masterchefdevs.colectiv.ubb.chefs.core.TAG
import masterchefdevs.colectiv.ubb.chefs.core.UnsecuredApi
import masterchefdevs.colectiv.ubb.chefs.data.model.Reservation
import masterchefdevs.colectiv.ubb.chefs.data.model.Restaurant
import masterchefdevs.colectiv.ubb.chefs.data.model.Table
import masterchefdevs.colectiv.ubb.chefs.data.model.Wall
import retrofit2.http.*
import java.util.Date

object RemoteRestaurantDataSource {

    interface RestaurantService {
        @Headers("Content-Type: application/json")
        @GET("/api/restaurant/{id}")
        suspend fun getRestaurant(@Path("id") id: Number): Restaurant

        @Headers("Content-Type: application/json")
        @GET("/api/mese/")
        suspend fun getTables(): List<Table>

        @Headers("Content-Type: application/json")
        @GET("/api/pereti/")
        suspend fun getWalls(): List<Wall>

        @Headers("Content-Type: application/json")
        @GET("/api/rezervari/")
        suspend fun getRezervari(): List<Reservation>      // cum trimit parametrii?
    }

    private val restaurantService: RestaurantService = UnsecuredApi.retrofit.create(RestaurantService::class.java)

    suspend fun getRestaurant(id: Number): Result<Restaurant> {
        Log.d(TAG, "inside getRestaurant _ restaurant repo")
        try {
            val a =restaurantService.getRestaurant(id)
            Log.d(TAG, "primit: "+a.nameR);
            val aa = Result.Success(a);
            Log.d(TAG, "primit: "+aa.data.nameR);
            return aa
        } catch (e: Exception) {
            return Result.Error(e)
        }
    }
    suspend fun getMese(id: Number): Result<List<Table>> {
        try {
            Log.d(TAG, "inainate de send req mese")
            val a = Result.Success(restaurantService.getTables())
            Log.d(TAG, "dupa de send req mese"+a.data.size)
            return a
        } catch (e: Exception) {
            return Result.Error(e)
        }
    }
    suspend fun getPereti(id: Number): Result<List<Wall>> {
        try {
            val a = Result.Success(restaurantService.getWalls())
            return a
        } catch (e: Exception) {
            return Result.Error(e)
        }
    }

    suspend fun getRezervari(id: Number, data: Date): Result<List<Reservation>> {
        Log.d(TAG, "inainate de send req rezervari")
        try {
            val a = Result.Success(restaurantService.getRezervari())
            Log.d(TAG, "dupa de send req rezervari")
            return a
        } catch (e: Exception) {
            return Result.Error(e)
        }

    }

}