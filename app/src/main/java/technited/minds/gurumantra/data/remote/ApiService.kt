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

    @FormUrlEncoded
    @POST("registration")
    suspend fun register(
        @Field("name")
        name: String?,
        @Field("email")
        email: String?,
        @Field("contact")
        contact: String?,
        @Field("password")
        password: String?
    ): Response<RegisterDetails>

    //    Home
    @GET("home")
    suspend fun getHome(): Response<Home>

    //    Live Class
    @GET("getMeetings/{batchNo}")
    suspend fun getMeetings(@Path("batchNo") batchNo: String): Response<MeetingDetails>

    @GET("batchDetails/{batchNo}")
    suspend fun getBatchDetails(@Path("batchNo") batchNo: String): Response<BatchDetailsItem>

    @GET("fetchMeeting/{classNo}")
    suspend fun getFetchMeeting(@Path("classNo") classNo: String): Response<FetchMeeting>

    @GET("getBatches")
    suspend fun getBatches(): Response<BatchDetails>


    //    Test Series
    @GET("getTs")
    suspend fun getTestSeries(): Response<TestSeries>

    @GET("tsDetails")
    suspend fun getTestSeriesDetails(
        @Query("uId") uId: String,
        @Query("tsId") tsId: String
    ): Response<TestDetails>

    @GET("listTests")
    suspend fun getListTests(@Query("tsId") tsId: String): Response<ListTests>

    @GET("startTest")
    suspend fun getStartTest(
        @Query("userId") userId: String,
        @Query("testId") testId: String
    ): Response<StartTest>

    @Headers("Content-Type: application/json")
    @POST("submitTest")
    suspend fun submitTest(@Body endTest: EndTest): Response<Result>

    //    enroll
    @GET("enrollTs")
    suspend fun getEnrolled(
        @Query("userId") userId: String,
        @Query("tsId") tsId: String
    ): Response<Enrolled>


    //    Practice Sets
    @GET("getSetSeries")
    suspend fun getSetSeries(): Response<TestSeries>

    @GET("getSetSeriesDetails")
    suspend fun getSetSeriesDetails(
        @Query("userId") uId: String,
        @Query("pssId") pssId: String
    ): Response<TestDetails>

    @GET("listSets")
    suspend fun getListSets(@Query("pssId") pssId: String): Response<ListTests>


    @GET("startSet")
    suspend fun getStartSet(
        @Query("userId") userId: String,
        @Query("psId") psId: String
    ): Response<StartTest>

    //    enroll

    @GET("enrollPracSet")
    suspend fun getEnrolledSet(
        @Query("userId") userId: String,
        @Query("pssId") pssId: String
    ): Response<Enrolled>


    //    PDF Tests
    @GET("getPDFts")
    suspend fun getPDF(): Response<TestSeries>

    @GET("getPDFtsDetails")
    suspend fun getPDFDetails(
        @Query("ptsId") pssId: String
    ): Response<ListTests>


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
    @GET("gallery")
    suspend fun getGallery(): Response<Gallery>


    @GET("getAllNotes")
    suspend fun getNotes(): Response<GetNotes>


    @GET("packages")
    suspend fun getPackages(): Response<Packages>

    @GET("getPackageOrderId")
    suspend fun getPaymentDataPackage(
        @Query("userId") userId: String,
        @Query("pckId") pckId: String
    ): Response<PaymentOrder>

    @GET("getOrderIdTseries")
    suspend fun getPaymentDataSeries(
        @Query("userId") userId: String,
        @Query("tsId") tsId: String
    ): Response<PaymentOrder>


    @GET("getOrderIdPracseries")
    suspend fun getPaymentDataPractice(
        @Query("userId") userId: String,
        @Query("pssId") pssId: String
    ): Response<PaymentOrder>

    @FormUrlEncoded
    @POST("purchasePackage")
    suspend fun purchasePackage(
        @Field("userId")
        userId: String?,
        @Field("orderId")
        orderId: String?,
        @Field("paymentId")
        paymentId: String?
    ): Response<String>

    @FormUrlEncoded
    @POST("purchaseTseries")
    suspend fun purchaseSeries(
        @Field("userId")
        userId: String?,
        @Field("orderId")
        orderId: String?,
        @Field("paymentId")
        paymentId: String?
    ): Response<String>

    @FormUrlEncoded
    @POST("purchasePracseries")
    suspend fun purchasePractice(
        @Field("userId")
        userId: String?,
        @Field("orderId")
        orderId: String?,
        @Field("paymentId")
        paymentId: String?
    ): Response<String>


//    getCourses
    @GET("getCourses")
    suspend fun getCourses(): Response<GetCourses>


}