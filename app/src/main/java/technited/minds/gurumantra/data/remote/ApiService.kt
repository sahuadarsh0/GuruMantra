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

    //    Home----------------------------------------------------------------------
    @GET("home")
    suspend fun getHome(): Response<Home>

    @GET("specialOffers")
    suspend fun getSpecialOffers(): Response<Home>

    //    Live Class----------------------------------------------------------------
//    @GET("getMeetings/{batchNo}")
//    suspend fun getMeetings(@Path("batchNo") batchNo: String): Response<MeetingDetails>
//
//    @GET("batchDetails/{batchNo}")
//    suspend fun getBatchDetails(@Path("batchNo") batchNo: String): Response<BatchDetailsItem>
//
//    @GET("fetchMeeting/{classNo}")
//    suspend fun getFetchMeeting(@Path("classNo") classNo: String): Response<FetchMeeting>
//
//    @GET("getBatches/")
//    suspend fun getBatches(): Response<Batches>


    //    Live Class v2----------------------------------------------------------------
    @GET("liveClassCon/")
    suspend fun getConfList(
        @Query("batchId") batchId: String
    ): Response<MeetingDetails>

    @GET("liveClass/")
    suspend fun getLiveList(
        @Query("batchId") batchId: String
    ): Response<MeetingDetails>

    @GET("conferenceClassDesc/")
    suspend fun getConfClassDesc(
        @Query("userId") userId: String,
        @Query("clsId") clsId: String,
    ): Response<FetchMeeting>

    @GET("liveClassDesc/")
    suspend fun getLiveClassDesc(
        @Query("userId") userId: String,
        @Query("lcsId") lcsId: String,
    ): Response<FetchMeeting>

    @GET("joinLiveClass/")
    suspend fun getJoinLiveClass(
        @Query("lcsId") lcsId: String,
    ): Response<JoinLiveClass>


    @GET("batch/details/")
    suspend fun getBatchDetails(
        @Query("userId") userId: String,
        @Query("batchId") batchId: String
    ): Response<BatchDetails>


    @GET("confClass/previousClasses/")
    suspend fun getConfPreviousClasses(
        @Query("clsId") clsId: String,
    ): Response<PreviousClasses>

    @GET("getPreviousLiveClass/")
    suspend fun getLivePreviousClasses(
        @Query("lcsId") lcsId: String,
    ): Response<PreviousClasses>


    @GET("getBatches/{type}")
    suspend fun getBatches(@Path("type") type: String): Response<Batches>

//    comments

    @GET("cls/getComments")
    suspend fun getConfComments(@Query("pcId") pcId: String): Response<GetComment>

    @FormUrlEncoded
    @POST("cls/postComment")
    suspend fun postConfComment(
        @Field("userId")
        userId: Int?,
        @Field("pcId")
        pcId: Int?,
        @Field("comment")
        comment: String?
    ): Response<CommentResponse>

    @GET("lcs/getComments")
    suspend fun getLiveComments(@Query("lcId") lcId: String): Response<GetComment>

    @FormUrlEncoded
    @POST("lcs/postComment")
    suspend fun postLiveComment(
        @Field("userId")
        userId: Int?,
        @Field("lcId")
        lcId: Int?,
        @Field("comment")
        comment: String?
    ): Response<CommentResponse>



    //    enroll
    @GET("enrollBatch")
    suspend fun getEnrolledBatch(
        @Query("userId") userId: String,
        @Query("batchId") batchId: String
    ): Response<Enrolled>

    //    Test Series---------------------------------------------------------------
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


    //    Practice Sets------------------------------------------------------------------------------
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


    //    PDF Tests-----------------------------------------------------------------------
    @GET("getPDFts")
    suspend fun getPDF(): Response<TestSeries>

    @GET("getPDFtsDetails")
    suspend fun getPDFDetails(
        @Query("ptsId") pssId: String
    ): Response<ListTests>


    //    getBlogs------------------------------------------------------------------------------
    @GET("getBlogs")
    suspend fun getBlogs(): Response<GetBlogs>

    @GET("getComments/{blogId}")
    suspend fun getComments(@Path("blogId") blogId: String): Response<GetComment>

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

    @GET("discussionForum")
    suspend fun getDiscussionForumDetail(
        @Query("dId") dId: String,
    ): Response<GetDcs>

    @GET("discussion/getComments/")
    suspend fun getDiscussionComments(
        @Query("dId") dId: String,
    ): Response<GetComment>

    @FormUrlEncoded
    @POST("discussion/postComment")
    suspend fun postDiscussionComment(
        @Field("userId")
        userId: Int?,
        @Field("dId")
        dId: Int?,
        @Field("comment")
        comment: String?
    ): Response<CommentResponse>

    @GET("filterBlog")
    suspend fun filterBlogs(
        @Query("cId") cId: String,
        @Query("sId") scId: String
    ): Response<GetBlogs>

    // Others------------------------------------------------------------------------------------------------------
    @GET("gallery")
    suspend fun getGallery(): Response<Gallery>

    // Notes Library


    @GET("getNotes")
    suspend fun getLibraryNotes(): Response<GetLibraryNotes>

    @GET("getSampleNotes")
    suspend fun getSampleNotes(): Response<GetNotes>

    @GET("getCaNotes")
    suspend fun getCaNotes(): Response<GetNotes>

    @GET("getNcertNotes")
    suspend fun getNcertNotes(): Response<GetNotes>

    @GET("getAllNotes")
    suspend fun getAllNotes(): Response<GetNotes>

    @GET("filterNotes")
    suspend fun filterNotes(
        @Query("cId") cId: String,
        @Query("sId") scId: String
    ): Response<GetNotes>


    @GET("packages")
    suspend fun getPackages(): Response<Packages>

    @GET("mycoupons")
    suspend fun getCoupons(
        @Query("userId") userId: String
    ): Response<Coupons>

    @GET("getPackageOrderId")
    suspend fun getPaymentDataPackage(
        @Query("userId") userId: String,
        @Query("pckId") pckId: String,
        @Query("couponCode") coupon: String? = null
    ): Response<PaymentOrder>

    @GET("getOrderIdTseries")
    suspend fun getPaymentDataSeries(
        @Query("userId") userId: String,
        @Query("tsId") tsId: String,
        @Query("couponCode") coupon: String? = null
    ): Response<PaymentOrder>


    @GET("getOrderIdPracseries")
    suspend fun getPaymentDataPractice(
        @Query("userId") userId: String,
        @Query("pssId") pssId: String,
        @Query("couponCode") coupon: String? = null
    ): Response<PaymentOrder>

    @GET("getCourseOrderId")
    suspend fun getPaymentDataCourse(
        @Query("userId") userId: String,
        @Query("cId") cId: String,
        @Query("couponCode") coupon: String? = null
    ): Response<PaymentOrder>

    @GET("getBatchOrderId")
    suspend fun getPaymentDataBatch(
        @Query("userId") userId: String,
        @Query("batchId") batchId: String,
        @Query("couponCode") coupon: String? = null
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

    @FormUrlEncoded
    @POST("purchaseCourse")
    suspend fun purchaseCourse(
        @Field("userId")
        userId: String?,
        @Field("orderId")
        orderId: String?,
        @Field("paymentId")
        paymentId: String?
    ): Response<String>

    @FormUrlEncoded
    @POST("purchaseBatch")
    suspend fun purchaseBatch(
        @Field("userId")
        userId: String?,
        @Field("orderId")
        orderId: String?,
        @Field("paymentId")
        paymentId: String?
    ): Response<String>

    @FormUrlEncoded
    @POST("orderPostalCourse")
    suspend fun purchasePostal(
        @Field("userId")
        userId: String?,
        @Field("orderId")
        orderId: String?,
        @Field("paymentId")
        paymentId: String?
    ): Response<String>


    @GET("getCatogery")
    suspend fun getCategory(): Response<GetCategory>

    @GET("getSubCatogery")
    suspend fun getSubCategory(
        @Query("cId") cId: String
    ): Response<GetSubCategory>

    //    getCourses-------------------------------------------------------------------------------
    @GET("getCourses")
    suspend fun getCourses(): Response<GetCourses>

    //    getCourseDetails
    @GET("getCourseDetails")
    suspend fun getCourseDetails(
        @Query("userId") userId: String,
        @Query("cid") cId: String
    ): Response<CourseDetails>

    //    enroll

    @GET("enrollCourse")
    suspend fun getEnrolledCourse(
        @Query("userId") userId: String,
        @Query("cId") cId: String
    ): Response<Enrolled>

    // getPostalCourses
    @GET("postalcourse")
    suspend fun getPostalCourses(): Response<PostalCourses>

    @Headers("Content-Type: application/json")
    @POST("pcs/submitUserDetails")
    suspend fun submitPostalAddress(@Body submitPostalAddress: SubmitPostalAddress): Response<PostalResult>

    @GET("user/pcs/orders")
    suspend fun getPostalOrders(
        @Query("userId") userId: String
    ): Response<OrderPostalCourses>

}