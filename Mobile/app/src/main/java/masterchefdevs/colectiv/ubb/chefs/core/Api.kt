package masterchefdevs.colectiv.ubb.chefs.core


import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Api {
    private const val URL = "https://master-chef-devs.herokuapp.com"

    val tokenInterceptor = TokenInterceptor()

    var logging: HttpLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    private val client: OkHttpClient = OkHttpClient.Builder().apply {
        this.addInterceptor(tokenInterceptor)
        this.addInterceptor(logging)
    }.build()

    private var gson = GsonBuilder()
        .setLenient()
        .create()

    val retrofit = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(client)
        .build()
}
