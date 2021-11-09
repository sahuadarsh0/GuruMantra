package technited.minds.gurumantra.data.remote

import retrofit2.Response
import retrofit2.http.*
import technited.minds.gurumantra.model.*


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

    @GET("fetchMeeting/{classNo}")
    suspend fun getFetchMeeting(@Path("classNo") classNo: String): Response<FetchMeeting>

    @GET("getBatches")
    suspend fun getBatches(): Response<BatchDetails>

//    @GET("rider/sendOTP/{mobile}/{otp}")
//    fun sendOtp(
//        @Path("mobile")
//        mobile: String,
//        @Path("otp")
//        otp: String?
//    ): Call<ResponseBody>


    //    Test Series
    @GET("getTs")
    suspend fun getTestSeries(): Response<TestSeries>

    @GET("tsDetails")
    suspend fun getTestSeriesDetails(
        @Query("tsId") tsId: String,
        @Query("uId") uId: String
    ): Response<TestDetails>


    @GET("listTests/{tsId}")
    suspend fun getListTests(@Path("tsId") tsId: String): Response<ListTests>


    @GET("startTest")
    suspend fun getStartTest(
        @Query("testId") testId: String,
        @Query("userId") userId: String
    ): Response<StartTest>

    @Headers("Content-Type: application/json")
    @POST("submitTest")
    suspend fun submitTest(@Body endTest: EndTest): Response<Result>

    //    getBlogs
    @GET("getBlogs")
    suspend fun getBlogs(): Response<GetBlogs>

    @GET("getComments/{blogId}")
    suspend fun getComments(@Path("blogId") blogId: String): Response<List<Comment>>

    @FormUrlEncoded
    @POST("postComment")
    suspend fun postComment(
        @Field("userId")
        userId: Int?,
        @Field("blogId")
        blogId: Int?,
        @Field("comment")
        comment: String?
    ): Response<CommentResponse>

    // Others
    @GET("Gallery")
    suspend fun getGallery(): Response<Gallery>


    @GET("Packages")
    suspend fun getPackages(): Response<Packages>



}