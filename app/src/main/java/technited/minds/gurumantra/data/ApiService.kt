package technited.minds.gurumantra.data

import android.util.Log
import androidx.room.FtsOptions
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import technited.minds.gurumantra.model.LoginDetails
import technited.minds.gurumantra.model.MeetingDetails
import technited.minds.gurumantra.utils.Constants


interface ApiService {

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("username")
        username: String?,
        @Field("password")
        password: String?
    ): Call<LoginDetails?>

//    @FormUrlEncoded
//    @POST("customer/cancelOrderStatus")
//    fun cancelOrderStatus(
//        @Field("order_id")
//        order_id: String?
//    ): Call<ResponseBody?>

    @GET("getMeetings")
    fun getMeetings(): Call<MeetingDetails?>

//    @GET("rider/sendOTP/{mobile}/{otp}")
//    fun sendOtp(
//        @Path("mobile")
//        mobile: String,
//        @Path("otp")
//        otp: String?
//    ): Call<ResponseBody>

    companion object {


        fun create(): ApiService {
            val builder = OkHttpClient.Builder()
            val httpLoggingInterceptor = HttpLoggingInterceptor { s: String? -> Log.d("asa", s!!) }

            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            builder.addInterceptor(httpLoggingInterceptor)

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(builder.build())
                .baseUrl(Constants.BASE_URL.toString())
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}