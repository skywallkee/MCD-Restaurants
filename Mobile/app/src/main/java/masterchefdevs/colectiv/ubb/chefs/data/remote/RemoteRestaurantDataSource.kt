package masterchefdevs.colectiv.ubb.chefs.data.remote

import android.util.Log
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.squareup.moshi.Json
import masterchefdevs.colectiv.ubb.chefs.core.Result
import masterchefdevs.colectiv.ubb.chefs.core.TAG
import masterchefdevs.colectiv.ubb.chefs.core.UnsecuredApi
import masterchefdevs.colectiv.ubb.chefs.data.model.*
import retrofit2.http.*
import java.util.Date

object RemoteRestaurantDataSource {

    interface RestaurantService {
        @Headers("Content-Type: application/json")
        @POST("/statisticsByDay")
        suspend fun getStatByDay(@Body id: IdDto): DayStatDTO

        @Headers("Content-Type: application/json")
        @POST("/statisticsByDayByHour")
        suspend fun getStatByHour(@Body id: IdDayDto): JsonElement

        @Headers("Content-Type: application/json")
        @POST("/getReviewAverage")
        suspend fun getRating(@Body id: IdDto): JsonElement

        @Headers("Content-Type: application/json")
        @GET("/api/restaurant/{id}")
        suspend fun getRestaurant(@Path("id") id: Number): Restaurant

        @Headers("Content-Type: application/json")
        @GET("/api/mese/")
        suspend fun getTables(): List<Table>

        @Headers("Content-Type: application/json")
        @POST("/api/pereti/")
        suspend fun getWalls(@Body id: Number): List<Wall>

        @Headers("Content-Type: application/json")
        @GET("/api/rezervari/")
        suspend fun getRezervari(): List<Reservation>

    }

    private val restaurantService: RestaurantService = UnsecuredApi.retrofit.create(RestaurantService::class.java)

    suspend fun getRating(id: Int): Result<Float>{
        try {
            val idDto = IdDto(id)
            val a = restaurantService.getRating(idDto)
            val value: Float =  (a as JsonObject).get("Average").asFloat
            return Result.Success(value)
        } catch (e: Exception) {
            return Result.Error(e)
        }
    }

    suspend fun getDayStat(id: Int): Result<DayStatDTO> {
        try {
            val idDto = IdDto(id)
            return Result.Success(restaurantService.getStatByDay(idDto))
        } catch (e: Exception) {
            return Result.Error(e)
        }
    }

    suspend fun getDayStatHour(id: Int, id_day: Int): Result<List<Int>> {

        Log.d(TAG, "inside stat_ day_hour repo")
        try {
            val idDayDto = IdDayDto(id, id_day)
            val a = restaurantService.getStatByHour(idDayDto)
            val a0 = (a as JsonObject).get("0-2").asInt
            val a2 = (a as JsonObject).get("2-4").asInt
            val a4 = (a as JsonObject).get("4-6").asInt
            val a6 = (a as JsonObject).get("6-8").asInt
            val a8 = (a as JsonObject).get("8-10").asInt
            val a10 = (a as JsonObject).get("10-12").asInt
            val a12 = (a as JsonObject).get("12-14").asInt
            val a14 = (a as JsonObject).get("14-16").asInt
            val a16 = (a as JsonObject).get("16-18").asInt
            val a18 = (a as JsonObject).get("18-20").asInt
            val a20 = (a as JsonObject).get("20-22").asInt
            val a22 = (a as JsonObject).get("22-24").asInt
            val lista: List<Int> = listOf(a0,a2,a4,a6,a8,a10,a12,a14,a16,a18,a20,a22)

            Log.d(TAG, "primit: ---" + a.toString());
            return Result.Success(lista)
        } catch (e: Exception) {
            return Result.Error(e)
        }
    }
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
            val a = Result.Success(restaurantService.getWalls(id))
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