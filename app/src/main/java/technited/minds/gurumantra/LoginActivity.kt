package technited.minds.gurumantra

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.afollestad.materialdialogs.MaterialDialog
import dagger.hilt.android.AndroidEntryPoint
import eightbitlab.com.blurview.RenderScriptBlur
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import technited.minds.gurumantra.data.remote.ApiService
import technited.minds.gurumantra.databinding.ActivityLoginBinding
import technited.minds.gurumantra.model.LoginDetails
import technited.minds.gurumantra.ui.LoginViewModel
import technited.minds.gurumantra.utils.ProcessDialog
import technited.minds.gurumantra.utils.SharedPrefs
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    @Inject
    lateinit var userSharedPreferences: SharedPrefs
    @Inject
    lateinit var processDialog: ProcessDialog
    private lateinit var loginData: LoginDetails
    private val loginViewModel: LoginViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_GuruMantra)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        if (!userSharedPreferences["name"].isNullOrEmpty()) {
            openMain()
        }
        binding.apply {
            loginButton.setOnClickListener {
                if (username.text.isNotEmpty() || password.text.isNotEmpty()) {
                    send(username.text.toString(),password.text.toString())
                } else {
                    Toast.makeText(this@LoginActivity, "Enter Username and Password", Toast.LENGTH_SHORT).show()
                }
            }
        }
        loginViewModel.memberItem.observe(this,{
            when(it.status){
                Resource.Status.LOADING -> Log.d("asa", "onCreate: loading")
                Resource.Status.SUCCESS ->{ Log.d("asa", "onCreate: SUCCESS")
                    userSharedPreferences["member_id"] = "AICP000004"
                    startActivity(i)
                    finish()
                }
                Resource.Status.ERROR -> Log.d("asa", "onCreate: ERROR")

            }
        })
        initBlur()
    }

    private fun openMain() {
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
        finish()
    }

    private fun send(username: String, password: String) {
        processDialog.show()
        val apiInterface = ApiService.create().login(username, password)
        apiInterface.enqueue(object : Callback<LoginDetails?> {
            override fun onResponse(call: Call<LoginDetails?>, response: Response<LoginDetails?>) {
                if (response.body()?.msg.equals("Login Success")) {
                    loginData = response.body()!!
                    loginData.loginData.apply {
                        userSharedPreferences["name"] = name
                        userSharedPreferences["email"] = email
                        userSharedPreferences["contact"] = contact
                        userSharedPreferences["type"] = type
                        userSharedPreferences["image"] = image
                        userSharedPreferences["package"] = packageX.toString()
                        userSharedPreferences["id"] = id.toString()
                        userSharedPreferences.apply()
                    }
                    openMain()
                }else{
                    MaterialDialog(this@LoginActivity).show {
                        title(text = "LOGIN ERROR")
                        message(text = "username or password incorrect")
                        cornerRadius(16f)
                        positiveButton(text = "OK") { dialog ->
                            dialog.dismiss()
                        }
                    }
                }
                processDialog.dismiss()
            }

            override fun onFailure(call: Call<LoginDetails?>, t: Throwable) {
                MaterialDialog(this@LoginActivity).show {
                    title(text = "API ERROR")
                    message(text = "")
                    cornerRadius(16f)
                    positiveButton(text = "OK") { dialog ->
                        dialog.dismiss()
                    }
                }
                processDialog.dismiss()
            }
        })
    }

    private fun initBlur() {
        val radius = 20f

        val decorView = window.decorView;
        //ViewGroup you want to start blur from. Choose root as close to BlurView in hierarchy as possible.
        val rootView = decorView.findViewById<ViewGroup>(android.R.id.content);
        //Set drawable to draw in the beginning of each blurred frame (Optional).
        //Can be used in case your layout has a lot of transparent space and your content
        //gets kinda lost after after blur is applied.
        val windowBackground = decorView.background;

        binding.blurView.setupWith(rootView)
            .setFrameClearDrawable(windowBackground)
            .setBlurAlgorithm(RenderScriptBlur(this))
            .setBlurRadius(radius)
            .setBlurAutoUpdate(true)
            .setHasFixedTransformationMatrix(true)
    }
}