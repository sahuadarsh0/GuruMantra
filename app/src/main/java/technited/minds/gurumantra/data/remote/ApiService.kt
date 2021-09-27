package technited.minds.gurumantra.data.remote

import retrofit2.Response
import retrofit2.http.*
import technited.minds.gurumantra.model.BatchDetails
import technited.minds.gurumantra.model.BatchDetailsItem
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
    ): Response<LoginDetails>

//    @FormUrlEncoded
//    @POST("customer/cancelOrderStatus")
//    fun cancelOrderStatus(
//        @Field("order_id")
//        order_id: String?
//    ): Call<ResponseBody?>

    @GET("getMeetings/{batchNo}")
    suspend fun getMeetings(@Path("batchNo") batchNo: String): Response<MeetingDetails>

    @GET("batchDetails/{batchNo}")
    suspend fun getBatchDetails(@Path("batchNo") batchNo: String): Response<BatchDetailsItem>

    @GET("getBatches")
    suspend fun getBatches(): Response<BatchDetails>

//    @GET("rider/sendOTP/{mobile}/{otp}")
//    fun sendOtp(
//        @Path("mobile")
//        mobile: String,
//        @Path("otp")
//        otp: String?
//    ): Call<ResponseBody>

}