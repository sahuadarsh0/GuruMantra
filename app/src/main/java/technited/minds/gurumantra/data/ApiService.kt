package technited.minds.gurumantra.data

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import technited.minds.gurumantra.model.BatchDetails
import technited.minds.gurumantra.model.LoginDetails
import technited.minds.gurumantra.model.MeetingDetails


interface ApiService {

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("username")
        username: String?,
        @Field("password")
        password: String?
    ): Response<LoginDetails?>

//    @FormUrlEncoded
//    @POST("customer/cancelOrderStatus")
//    fun cancelOrderStatus(
//        @Field("order_id")
//        order_id: String?
//    ): Call<ResponseBody?>

    @GET("getMeetings")
    suspend fun getMeetings(): Response<MeetingDetails?>

    @GET("getBatches")
    suspend fun getBatches(): Response<BatchDetails?>

//    @GET("rider/sendOTP/{mobile}/{otp}")
//    fun sendOtp(
//        @Path("mobile")
//        mobile: String,
//        @Path("otp")
//        otp: String?
//    ): Call<ResponseBody>

}