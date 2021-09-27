package technited.minds.gurumantra

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.afollestad.materialdialogs.MaterialDialog
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import technited.minds.gurumantra.BuildConfig
import technited.minds.gurumantra.data.ApiService
import technited.minds.gurumantra.databinding.ActivityMainBinding
import technited.minds.gurumantra.model.MeetingDetails
import us.zoom.sdk.*

class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_GuruMantra)
        super.onCreate(savedInstanceState)
        activityMainBinding =DataBindingUtil.setContentView(this,R.layout.activity_main)
        initializeSdk(this)
        activityMainBinding.join.setOnClickListener{
            getMeetings()
        }
    }

    private fun getMeetings() {
        val apiService = ApiService.create().getMeetings()
        apiService.enqueue(object : Callback<MeetingDetails?>{
            override fun onResponse(call: Call<MeetingDetails?>, response: Response<MeetingDetails?>) {
                if (response.body()?.details!!.isNotEmpty()) {
                   val meeting  = response.body()!!
                    meeting.details.apply {
                        if (this[0].meetingId.isNotEmpty() && this[0].mPassword.isNotEmpty() ) {
                            Log.d("asa", "onResponse: ${this[0].meetingId} and ,${this[0].mPassword}")
                            joinMeeting(this@MainActivity,this[0].meetingId,this[0].mPassword)
                        }
                    }
                }else{
                    MaterialDialog(this@MainActivity).show {
                        title(text = "PACKAGE ERROR")
                        message(text = "Unable to Retrieve package details")
                        cornerRadius(16f)
                        positiveButton(text = "OK") { dialog ->
                            dialog.dismiss()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<MeetingDetails?>, t: Throwable) {
                MaterialDialog(this@MainActivity).show {
                    title(text = "API ERROR")
                    message(text = "")
                    cornerRadius(16f)
                    positiveButton(text = "OK") { dialog ->
                        dialog.dismiss()
                    }
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
                if (errorCode== ZoomError.ZOOM_ERROR_SUCCESS){
                    getMeetings()
                }
            }
            override fun onZoomAuthIdentityExpired() = Unit
        }

        sdk.initialize(context, listener, params)

    }

    // 1. Add joinMeeting feature
    private fun joinMeeting(context: Context, meetingNumber: String, pw: String) {
        val meetingService = ZoomSDK.getInstance().meetingService
        val options = JoinMeetingOptions()
        val params = JoinMeetingParams().apply {
            displayName = "Asa" // TODO: Enter your name

            meetingNo = meetingNumber
            password = pw
        }
        meetingService.joinMeetingWithParams(context, params, options)
    }


}