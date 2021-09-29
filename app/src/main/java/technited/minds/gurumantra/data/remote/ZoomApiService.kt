package technited.minds.gurumantra.data.remote

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import technited.minds.gurumantra.model.*


interface ZoomApiService {

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
        "Platform: android")
    @GET("sMXMlFsJTaSFRqz7pkiASw/token/?type=zak")
    fun getToken(@Header("Authorization") token: String,
    ): Call<ZoomToken>

    companion object {
        private var BASE_URL = "https://api.zoom.us/v2/users/"
        fun create(): ZoomApiService {
            val builder = OkHttpClient.Builder()
            val httpLoggingInterceptor = HttpLoggingInterceptor { s: String? -> Log.d("asa", s!!) }

            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            builder.addInterceptor(httpLoggingInterceptor)

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(builder.build())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ZoomApiService::class.java)
        }
    }
}