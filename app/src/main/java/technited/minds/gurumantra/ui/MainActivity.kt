package technited.minds.gurumantra.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import technited.minds.gurumantra.BuildConfig
import technited.minds.gurumantra.R
import technited.minds.gurumantra.databinding.ActivityMainBinding
import technited.minds.gurumantra.utils.SharedPrefs
import us.zoom.sdk.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    @Inject
    lateinit var userSharedPreferences: SharedPrefs
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_GuruMantra)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val bottomNavigationView: BottomNavigationView = binding.navView
        navController = findNavController(R.id.nav_host_fragment_activity_main)
        bottomNavigationView.setupWithNavController(navController)

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    navController.navigate(R.id.navigation_home)
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_dashboard -> {
                    navController.navigate(R.id.navigation_dashboard)
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_notifications -> {
                    navController.navigate(R.id.navigation_notifications)
                    return@setOnItemSelectedListener true
                }

                R.id.logout -> {
                    MaterialDialog(this).show {
                        title(text = "Logout?")
                        message(text = "Are you sure want to logout ?")
                        cornerRadius(16f)
                        positiveButton(text = "Yes") { dialog ->
                            userSharedPreferences.clearAll()
                            dialog.dismiss()
                            val i = Intent(this@MainActivity, LoginActivity::class.java)
                            startActivity(i)
                            finish()
                        }
                        negativeButton(text = "Cancel") { dialog ->
                            dialog.dismiss()
                        }
                    }
                    return@setOnItemSelectedListener true
                }
                else -> Toast.makeText(this, "Select an item", Toast.LENGTH_SHORT).show()
            }

            false
        }
    }
//
//    private fun getMeetings() {
//        val apiService = ApiService.create().getMeetings()
//        apiService.enqueue(object : Callback<MeetingDetails?>{
//            override fun onResponse(call: Call<MeetingDetails?>, response: Response<MeetingDetails?>) {
//                if (response.body()?.details!!.isNotEmpty()) {
//                   val meeting  = response.body()!!
//                    meeting.details.apply {
//                        if (this[0].meetingId.isNotEmpty() && this[0].mPassword.isNotEmpty() ) {
//                            Log.d("asa", "onResponse: ${this[0].meetingId} and ,${this[0].mPassword}")
//                            joinMeeting(this@MainActivity,this[0].meetingId,this[0].mPassword)
//                        }
//                    }
//                }else{
//                    MaterialDialog(this@MainActivity).show {
//                        title(text = "PACKAGE ERROR")
//                        message(text = "Unable to Retrieve package details")
//                        cornerRadius(16f)
//                        positiveButton(text = "OK") { dialog ->
//                            dialog.dismiss()
//                        }
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<MeetingDetails?>, t: Throwable) {
//                MaterialDialog(this@MainActivity).show {
//                    title(text = "API ERROR")
//                    message(text = "")
//                    cornerRadius(16f)
//                    positiveButton(text = "OK") { dialog ->
//                        dialog.dismiss()
//                    }
//                }
//            }
//        })
//    }

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
//                    getMeetings()
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
            displayName = "Asa" // TODO: Enter your name
            meetingNo = meetingNumber
            password = pw
        }
        meetingService.joinMeetingWithParams(context, params, options)
    }


}