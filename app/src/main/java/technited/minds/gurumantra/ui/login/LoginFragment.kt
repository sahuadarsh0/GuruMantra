package technited.minds.gurumantra.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import dagger.hilt.android.AndroidEntryPoint
import eightbitlab.com.blurview.RenderScriptBlur
import technited.minds.gurumantra.R
import technited.minds.gurumantra.databinding.FragmentLoginBinding
import technited.minds.gurumantra.model.LoginDetails
import technited.minds.gurumantra.ui.MainActivity
import technited.minds.gurumantra.utils.Resource
import technited.minds.gurumantra.utils.SharedPrefs
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null

    @Inject
    lateinit var userSharedPreferences: SharedPrefs
    private lateinit var loginDetails: LoginDetails
    private val loginViewModel: LoginViewModel by viewModels()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val root: View = binding.root
        initBlur()
        setup()
        binding.registerText.setOnClickListener {
            it.findNavController().navigate(R.id.action_navigation_login_to_navigation_register)
        }
        return root
    }


    private fun setup() {
        binding.apply {
            loginButton.setOnClickListener {
                if (email.text.isNotEmpty() || password.text.isNotEmpty()) {
                    send(email.text.toString(), password.text.toString())
                } else {
                    Toast.makeText(requireContext(), "Enter Email and Password", Toast.LENGTH_SHORT).show()
                }
            }
            loginViewModel.loginDetails.observe(viewLifecycleOwner, {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        animationView.visibility = VISIBLE
                        items.visibility = GONE
                    }
                    Resource.Status.SUCCESS -> {
                        if (it.data?.msg.equals("Login Success")) {
                            loginDetails = it.data!!
                            loginDetails.loginData.apply {
                                userSharedPreferences["name"] = name
                                userSharedPreferences["email"] = email
                                userSharedPreferences["contact"] = contact
                                userSharedPreferences["type"] = type
                                userSharedPreferences["image"] = image
                                userSharedPreferences["package"] = packageX.toString()
                                userSharedPreferences["phoneVerified"] = phoneVerified.toString()
                                userSharedPreferences["id"] = id.toString()
                                userSharedPreferences.apply()
                            }
                            openMain()
                        } else {
                            MaterialDialog(requireContext()).show {
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
                        MaterialDialog(requireContext()).show {
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
        }
    }

    private fun openMain() {
        val i = Intent(requireContext(), MainActivity::class.java)
        activity?.startActivity(i)
        activity?.finish()
    }

    private fun send(email: String, password: String) {
        binding.animationView.visibility = VISIBLE
        binding.items.visibility = GONE
        loginViewModel.login(email, password)
    }

    private fun initBlur() {
        val radius = 20f

        val decorView = activity?.window?.decorView!!
        //ViewGroup you want to start blur from. Choose root as close to BlurView in hierarchy as possible.
        val rootView = decorView.findViewById<ViewGroup>(android.R.id.content);
        //Set drawable to draw in the beginning of each blurred frame (Optional).
        //Can be used in case your layout has a lot of transparent space and your content
        //gets kinda lost after after blur is applied.
        val windowBackground = decorView.background;

        binding.blurView.setupWith(rootView)
            .setFrameClearDrawable(windowBackground)
            .setBlurAlgorithm(RenderScriptBlur(requireContext()))
            .setBlurRadius(radius)
            .setBlurAutoUpdate(true)
            .setHasFixedTransformationMatrix(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}