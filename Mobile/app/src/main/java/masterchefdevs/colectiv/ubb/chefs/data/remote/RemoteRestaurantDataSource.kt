package masterchefdevs.colectiv.ubb.chefs.data.remote

import android.util.Log
import masterchefdevs.colectiv.ubb.chefs.auth.data.LoggedInUser
import masterchefdevs.colectiv.ubb.chefs.auth.data.NewUser
import masterchefdevs.colectiv.ubb.chefs.auth.data.TokenHolder
import masterchefdevs.colectiv.ubb.chefs.core.Api
import masterchefdevs.colectiv.ubb.chefs.core.Result
import masterchefdevs.colectiv.ubb.chefs.core.TAG
import masterchefdevs.colectiv.ubb.chefs.data.model.Restaurant
import masterchefdevs.colectiv.ubb.chefs.data.model.Table
import retrofit2.http.*

object RemoteRestaurantDataSource {

    interface RestaurantService {
    @Headers("Content-Type: application/json")
    @GET("/api/restaurant/")
    suspend fun getRestaurant(@Path("id") id: Number): Restaurant

    @Headers("Content-Type: application/json")
    @GET("/api/mese/")
    suspend fun getTables(@Body id: Number): List<Table>
}

    private val restaurantService: RestaurantService = Api.retrofit.create(RestaurantService::class.java)

    suspend fun getRestaurant(id: Number): Result<Restaurant> {
        Log.d(TAG, "inside getRestaurant _ restaurant repo")
        try {
            val a =restaurantService.getRestaurant(1)
            Log.d(TAG, "primit: "+a.name);
            val aa = Result.Success(a);
            Log.d(TAG, "primit: "+aa.data.name);
            return aa
        } catch (e: Exception) {
            return Result.Error(e)

        }
    }
    suspend fun getTables(id: Number): Result<List<Table>> {
        try {
            val a = Result.Success(restaurantService.getTables(id))
            return a
        } catch (e: Exception) {
            return Result.Error(e)
        }
    }

}