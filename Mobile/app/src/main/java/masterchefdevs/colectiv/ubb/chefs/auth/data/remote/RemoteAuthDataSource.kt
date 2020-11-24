package masterchefdevs.colectiv.ubb.chefs.auth.data.remote

import android.util.Log
import masterchefdevs.colectiv.ubb.chefs.core.Api
import  masterchefdevs.colectiv.ubb.chefs.core.Result
import masterchefdevs.colectiv.ubb.chefs.auth.data.LoggedInUser
import masterchefdevs.colectiv.ubb.chefs.auth.data.NewUser
import masterchefdevs.colectiv.ubb.chefs.auth.data.TokenHolder
import masterchefdevs.colectiv.ubb.chefs.core.TAG
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

object RemoteAuthDataSource {
    interface AuthService {
        @Headers("Content-Type: application/json")
        @POST("/rest-auth/login/")
        suspend fun login(@Body user: LoggedInUser): TokenHolder

        @Headers("Content-Type: application/json")
        @POST("/createUser/")
        suspend fun register(@Body user: NewUser): TokenHolder
    }

    private val authService: AuthService = Api.retrofit.create(AuthService::class.java)

    suspend fun login(user: LoggedInUser): Result<TokenHolder> {
        try {
            val a = Result.Success(authService.login(user))
            return a
        } catch (e: Exception) {
            return Result.Error(e)
        }
    }
    suspend fun register(user: NewUser): Result<TokenHolder> {
        try {
            return Result.Success(authService.register(user))
        } catch (e: Exception) {
            return Result.Error(e)
        }
    }
}

