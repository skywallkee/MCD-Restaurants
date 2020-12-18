package masterchefdevs.colectiv.ubb.chefs.data.remote

import com.google.gson.GsonBuilder
import masterchefdevs.colectiv.ubb.chefs.core.TokenInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiGen {
    private const val URL = "https://master-chef-devs.herokuapp.com"

    private val client: OkHttpClient = OkHttpClient.Builder().build()

    private var gson = GsonBuilder()
        .setLenient()
        .create()

    val retrofit = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(client)
        .build()
}
