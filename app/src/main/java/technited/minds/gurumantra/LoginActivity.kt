package technited.minds.gurumantra

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.afollestad.materialdialogs.MaterialDialog
import dagger.hilt.android.AndroidEntryPoint
import eightbitlab.com.blurview.RenderScriptBlur
import technited.minds.gurumantra.databinding.ActivityLoginBinding
import technited.minds.gurumantra.model.LoginDetails
import technited.minds.gurumantra.ui.LoginViewModel
import technited.minds.gurumantra.utils.Resource
import technited.minds.gurumantra.utils.SharedPrefs
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    @Inject
    lateinit var userSharedPreferences: SharedPrefs
    private lateinit var loginData: LoginDetails
    private val loginViewModel: LoginViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_GuruMantra)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.animationView.visibility = GONE
        if (!userSharedPreferences["name"].isNullOrEmpty()) {
            openMain()
        }
        binding.apply {
            loginButton.setOnClickListener {
                if (username.text.isNotEmpty() || password.text.isNotEmpty()) {
                    send(username.text.toString(), password.text.toString())
                } else {
                    Toast.makeText(this@LoginActivity, "Enter Username and Password", Toast.LENGTH_SHORT).show()
                }
            }

            loginViewModel.details.observe(this@LoginActivity, {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        animationView.visibility = VISIBLE
                        items.visibility = GONE
                    }
                    Resource.Status.SUCCESS -> {
                        Log.d("asa", "onCreate: SUCCESS")
                        if (it.data?.msg.equals("Login Success")) {
                            loginData = it.data!!
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
                        } else {
                            MaterialDialog(this@LoginActivity).show {
                                title(text = "LOGIN ERROR")
                                message(text = "username or password incorrect")
                                cornerRadius(16f)
                                positiveButton(text = "OK") { dialog ->
                                    dialog.dismiss()
                                }
                            }
                        }
                        animationView.visibility = GONE
                        items.visibility = VISIBLE
                    }
                    Resource.Status.ERROR -> {
                        MaterialDialog(this@LoginActivity).show {
                            title(text = "API ERROR")
                            message(text = it.message)
                            cornerRadius(16f)
                            positiveButton(text = "OK") { dialog ->
                                dialog.dismiss()
                            }
                        }
                        animationView.visibility = GONE
                        items.visibility = VISIBLE
                    }
                }

            })
            initBlur()
        }
    }

        private fun openMain() {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            finish()
        }

        private fun send(username: String, password: String) {
            binding.animationView.visibility = VISIBLE
            binding.items.visibility = GONE
            loginViewModel.login(username, password)
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