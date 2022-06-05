package technited.minds.gurumantra.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import technited.minds.gurumantra.databinding.FragmentRegisterBinding
import technited.minds.gurumantra.model.RegisterDetails
import technited.minds.gurumantra.ui.MainActivity
import technited.minds.gurumantra.utils.Resource
import technited.minds.gurumantra.utils.SharedPrefs
import javax.inject.Inject

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null

    @Inject
    lateinit var userSharedPreferences: SharedPrefs
    private lateinit var registerDetails: RegisterDetails
    private val loginViewModel: LoginViewModel by viewModels()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        val root: View = binding.root
        initBlur()
        setup()
        binding.loginText.setOnClickListener {
            it.findNavController().navigate(R.id.action_navigation_register_to_navigation_login)
        }
        return root
    }


    private fun setup() {

        binding.apply {
            registerButton.setOnClickListener {
                if (name.text.isNotEmpty() || email.text.isNotEmpty() || contact.text.isNotEmpty() || password.text.isNotEmpty()) {
                    send(name.text.toString(), email.text.toString(), contact.text.toString(), password.text.toString())
                } else {
                    Toast.makeText(requireContext(), "All fields are mandatory", Toast.LENGTH_SHORT).show()
                }
            }

            loginViewModel.registerDetails.observe(viewLifecycleOwner) {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        animationView.visibility = VISIBLE
                        items.visibility = GONE
                    }
                    Resource.Status.SUCCESS -> {
                        if (it.data?.message.equals("OTP Send to Your Phone")) {
                            registerDetails = it.data!!
                            registerDetails.users?.apply {
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
                                title(text = "Message")
                                message(text = it.data?.message)
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

            }

        }
    }

    private fun openMain() {
        val i = Intent(requireContext(), MainActivity::class.java)
        activity?.startActivity(i)
        activity?.finish()
    }

    private fun send(name: String, email: String, contact: String, password: String) {
        binding.animationView.visibility = VISIBLE
        binding.items.visibility = GONE
        loginViewModel.register(name, email, contact, password)
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