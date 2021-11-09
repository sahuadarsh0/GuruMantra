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

        bottomNavigationView.itemIconTintList = null
        bottomNavigationView.itemBackground = null
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    navController.navigate(R.id.navigation_home)
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_test_series -> {
                    navController.navigate(R.id.navigation_test_series)
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_blogs -> {
                    navController.navigate(R.id.navigation_blogs)
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