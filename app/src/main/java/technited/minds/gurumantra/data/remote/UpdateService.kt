package technited.minds.gurumantra.data.remote

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import technited.minds.gurumantra.model.App
import technited.minds.gurumantra.utils.Constants

interface UpdateService {


    @GET("getAppVersion/")
    fun check(): Call<App>

    @FormUrlEncoded
    @POST("updateFCMToken/")
    fun updateToken(@Field("fcmToken") token: String?): Call<App>

    companion object {


        fun create(): UpdateService {
            val builder = OkHttpClient.Builder()
            val httpLoggingInterceptor = HttpLoggingInterceptor { s: String? -> Log.d("ASA", s!!) }

            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            builder.addInterceptor(httpLoggingInterceptor)

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(builder.build())
                .baseUrl(Constants.BASE_URL.toString())
                .build()
            return retrofit.create(UpdateService::class.java)
        }
    }
}
