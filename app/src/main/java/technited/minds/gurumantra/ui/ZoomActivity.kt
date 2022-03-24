package technited.minds.gurumantra.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.navArgs
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import technited.minds.gurumantra.BuildConfig
import technited.minds.gurumantra.R
import technited.minds.gurumantra.data.remote.ZoomApiService
import technited.minds.gurumantra.databinding.ActivityZoomBinding
import technited.minds.gurumantra.model.ZoomToken
import technited.minds.gurumantra.utils.Resource
import technited.minds.gurumantra.utils.SharedPrefs
import us.zoom.sdk.*
import javax.inject.Inject
import us.zoom.sdk.StartMeetingOptions


@AndroidEntryPoint
class ZoomActivity : AppCompatActivity() {

    @Inject
    lateinit var userSharedPreferences: SharedPrefs
    private val liveViewModel: LiveViewModel by viewModels()
    private lateinit var binding: ActivityZoomBinding
    private val args: ZoomActivityArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_GuruMantra)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_zoom)
        initializeSdk(this)
        setUpObservers()
        getToken()
    }

    private fun getToken() {
        val token = ZoomApiService.create().getToken(token = "Bearer ${BuildConfig.SDK_JWTTOKEN}")
        token.enqueue(object : Callback<ZoomToken> {
            override fun onResponse(call: Call<ZoomToken>, response: Response<ZoomToken>) {
                response.body()?.token.let { userSharedPreferences["token"] = it }
            }

            override fun onFailure(call: Call<ZoomToken>, t: Throwable) {
            }
        })
    }

    private fun setUpObservers() {
        liveViewModel.meeting.observe(this, {
            when (it.status) {
                Resource.Status.LOADING -> {
                    binding.animationView.visibility = View.VISIBLE

                }
                Resource.Status.SUCCESS -> {
                    val meetings = it.data

                    if (meetings != null) {
                        if (userSharedPreferences["type"].equals("Admin") || userSharedPreferences["type"].equals("Faculty"))
                            startMeetingWithNumber(
                                context = this@ZoomActivity,
                                meetingNo = meetings.cls.meetingId,
                                userId = "sMXMlFsJTaSFRqz7pkiASw",
                                zak = userSharedPreferences["token"]
                            )
                        else
                            joinMeeting(this@ZoomActivity, meetings.cls.meetingId, meetings.cls.mPassword)

                        binding.animationView.visibility = View.GONE
                    }

                }
                Resource.Status.ERROR -> {
                    binding.animationView.visibility = View.GONE

                }

            }
        })
    }


    private fun initializeSdk(context: Context) {
        val sdk = ZoomSDK.getInstance()

        // TODO: Do not use hard-coded values for your key/secret in your app in production!
        val params = ZoomSDKInitParams().apply {
            appKey = BuildConfig.SDK_KEY
            appSecret = BuildConfig.SDK_SECRET
            domain = "zoom.us"
            enableLog = true // Optional: enable logging for debugging
        }
        // TODO (optional): Add functionality to this listener (e.g. logs for debugging)
        val listener = object : ZoomSDKInitializeListener {
            /**
             * If the [errorCode] is [ZoomError.ZOOM_ERROR_SUCCESS], the SDK was initialized and can
             * now be used to join/start a meeting.
             */
            override fun onZoomSDKInitializeResult(errorCode: Int, internalErrorCode: Int) {
                if (errorCode == ZoomError.ZOOM_ERROR_SUCCESS) {
                    liveViewModel.fetchMeeting(userSharedPreferences["id"]!!,args.classNo,0)
                }
            }

            override fun onZoomAuthIdentityExpired() = Unit
        }

        sdk.initialize(context, listener, params)

    }

    private fun joinMeeting(context: Context, meetingNumber: String, pw: String) {
        val meetingService = ZoomSDK.getInstance().meetingService
        val options = JoinMeetingOptions()
        val params = JoinMeetingParams().apply {
            displayName = userSharedPreferences["name"]
            meetingNo = meetingNumber
            password = pw
        }
        meetingService.joinMeetingWithParams(context, params, options)
    }

    private fun startMeetingWithNumber(context: Context?, meetingNo: String?, userId: String, zak: String?): Int {
        var ret = -1
        val meetingService: MeetingService = ZoomSDK.getInstance().meetingService
        val opts = StartMeetingOptions()
        val params = StartMeetingParamsWithoutLogin()
        params.userId = userId
        params.userType = MeetingService.USER_TYPE_API_USER
        params.displayName = userSharedPreferences["name"]
        params.zoomAccessToken = zak
        params.meetingNo = meetingNo
        ret = meetingService.startMeetingWithParams(context, params, opts)
        Log.i("asa", "startMeetingWithNumber, ret=$ret")
        return ret
    }

}