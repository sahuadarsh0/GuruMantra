package technited.minds.gurumantra.data.remote

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import technited.minds.gurumantra.model.Grant
import technited.minds.gurumantra.utils.Constants

interface UpdateService {


    @GET("permissions/{website}/")
    fun check(
        @Path("website")
        website: String?
    ): Call<Grant>

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
