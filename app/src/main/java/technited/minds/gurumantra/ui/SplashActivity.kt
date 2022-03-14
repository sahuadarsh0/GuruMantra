package technited.minds.gurumantra.ui

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import technited.minds.gurumantra.R
import technited.minds.gurumantra.data.remote.Service
import technited.minds.gurumantra.model.Grant
import technited.minds.gurumantra.ui.login.LoginActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()
        setTheme(R.style.Theme_GuruMantra)
        setContentView(R.layout.activity_splash)
        window.navigationBarColor = resources.getColor(R.color.white)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = this.resources.getColor(R.color.white)
        check()
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
                            val i = Intent(this@SplashActivity, LoginActivity::class.java)
                            startActivity(i)
                            finish()
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


}