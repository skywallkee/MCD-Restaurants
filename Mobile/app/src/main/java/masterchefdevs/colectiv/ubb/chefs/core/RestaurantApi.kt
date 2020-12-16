package masterchefdevs.colectiv.ubb.chefs.core

import masterchefdevs.colectiv.ubb.chefs.data.model.Restaurant
import retrofit2.http.*

object RestaurantApi {

    interface Service {
        @GET("/api/restaurant")
        suspend fun find(): List<Restaurant>

        @GET("/api/restaurant/{id}")
        suspend fun read(@Path("id") itemId: String): Restaurant;

        @Headers("Content-Type: application/json")
        @POST("/api/restaurant")
        suspend fun create(@Body item: Restaurant): Restaurant

        @Headers("Content-Type: application/json")
        @PUT("/api/restaurant/{id}")
        suspend fun update(@Path("id") itemId: String, @Body item: Restaurant): Restaurant
    }
    val service: Service = UnsecuredApi.retrofit.create(Service::class.java)
}