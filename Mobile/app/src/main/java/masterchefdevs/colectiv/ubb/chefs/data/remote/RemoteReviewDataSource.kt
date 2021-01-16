package masterchefdevs.colectiv.ubb.chefs.data.remote

import com.google.gson.JsonElement
import masterchefdevs.colectiv.ubb.chefs.core.UnsecuredApi
import masterchefdevs.colectiv.ubb.chefs.data.model.*
import retrofit2.http.*

object RemoteReviewDataSource {
    interface Service {
        @GET("/api/review")
        suspend fun find(): List<Review>

        @GET("/api/review/{id}")
        suspend fun read(@Path("id") itemId: Number): Review;

        @Headers("Content-Type: application/json")
        @POST("/api/review")
        suspend fun create(@Body item: Review): Review

        @Headers("Content-Type: application/json")
        @PUT("/api/review/{id}")
        suspend fun update(@Path("id") itemId: Number, @Body item: Review): Review
    }
     val service: Service = UnsecuredApi.retrofit.create(Service::class.java)
}