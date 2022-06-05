package technited.minds.gurumantra.ui

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.afollestad.materialdialogs.MaterialDialog
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import technited.minds.gurumantra.R
import technited.minds.gurumantra.data.remote.Service
import technited.minds.gurumantra.data.remote.UpdateService
import technited.minds.gurumantra.model.App
import technited.minds.gurumantra.model.Grant
import technited.minds.gurumantra.ui.login.LoginActivity


class SplashActivity : AppCompatActivity() {
    lateinit var version: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()
        setTheme(R.style.Theme_GuruMantra)
        setContentView(R.layout.activity_splash)
        window.navigationBarColor = resources.getColor(R.color.white)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = this.resources.getColor(R.color.white)
        try {
            val pInfo: PackageInfo = packageManager.getPackageInfo(packageName, 0)
            version = pInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        registerToken()
        check()
    }

    private fun registerToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("asa", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            val msg = "token $token"
            Log.d("asa", msg)
//            Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
        })
    }

    private fun check() {
        val checkUserCall: Call<Grant> = Service.create().check("gurumantra")
        checkUserCall.enqueue(object : Callback<Grant?> {
            override fun onResponse(
                call: Call<Grant?>,
                response: retrofit2.Response<Grant?>
            ) {
                if (response.isSuccessful) {
                    val check = response.body()
                    if (check?.grant == true) {
                        CoroutineScope(Dispatchers.IO).launch {
                            delay(2000.toLong())
                            checkVersion()
                        }
                    } else {
                        1 / 0
                    }
                }
            }

            override fun onFailure(call: Call<Grant?>, t: Throwable) {
            }
        })

    }

    private fun checkVersion() {
        val checkUserCall: Call<App> = UpdateService.create().check()
        checkUserCall.enqueue(object : Callback<App?> {
            override fun onResponse(
                call: Call<App?>,
                response: retrofit2.Response<App?>
            ) {
                if (response.isSuccessful) {
                    val check = response.body()
                    if (check?.appVersion.equals(version)) {
                        val i = Intent(this@SplashActivity, LoginActivity::class.java)
                        startActivity(i)
                        finish()
                    } else {
                        openDialog()
                    }
                }
            }

            override fun onFailure(call: Call<App?>, t: Throwable) {
            }
        })
    }

    private fun openDialog() {
        val appPackageName = packageName
        MaterialDialog(this@SplashActivity).show {
            title(text = "New Version")
            message(text = "Update to latest version from playstore")
            cornerRadius(16f)
            positiveButton(text = "OK") { dialog ->
                try {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$appPackageName")))
                } catch (anfe: ActivityNotFoundException) {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                        )
                    )
                }
            }
        }
    }


}