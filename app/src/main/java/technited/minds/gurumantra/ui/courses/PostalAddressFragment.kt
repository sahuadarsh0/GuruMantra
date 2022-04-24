package technited.minds.gurumantra.ui.courses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.afollestad.materialdialogs.MaterialDialog
import dagger.hilt.android.AndroidEntryPoint
import technited.minds.gurumantra.databinding.FragmentPostalAddressBinding
import technited.minds.gurumantra.model.RegisterDetails
import technited.minds.gurumantra.model.SubmitPostalAddress
import technited.minds.gurumantra.utils.Resource
import technited.minds.gurumantra.utils.SharedPrefs
import javax.inject.Inject

@AndroidEntryPoint
class PostalAddressFragment : Fragment() {

    private var _binding: FragmentPostalAddressBinding? = null

    @Inject
    lateinit var userSharedPreferences: SharedPrefs
    private lateinit var registerDetails: RegisterDetails
    private val coursesViewModel: CoursesViewModel by viewModels()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostalAddressBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setup()
        return root
    }


    private fun setup() {

        binding.apply {
            continueOrder.setOnClickListener {
                if (name.text.isNotEmpty() || email.text.isNotEmpty() || city.text.isNotEmpty() || state.text.isNotEmpty()
                    || district.text.isNotEmpty() || postalCode.text.isNotEmpty() || mobile.text.isNotEmpty() || address.text
                        .isNotEmpty()) {

                    val submitPostalAddress = SubmitPostalAddress(
                        pcsId = arguments?.getString("id")!!.toInt(),
                        userId = userSharedPreferences["id"]!!.toInt(),
                        name = name.text.toString(),
                        email = email.text.toString(),
                        phone= mobile.text.toString(),
                        city= city.text.toString(),
                        state= state.text.toString(),
                        pcode= postalCode.text.toString(),
                        district= district.text.toString(),
                        address= address.text.toString()
                    )
                    send(submitPostalAddress)
                } else {
                    Toast.makeText(requireContext(), "All fields are mandatory", Toast.LENGTH_SHORT).show()
                }
            }

            coursesViewModel.submitPostalResult.observe(viewLifecycleOwner, {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        animationView.visibility = VISIBLE
                    }
                    Resource.Status.SUCCESS -> {

                        animationView.visibility = GONE
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
                    }
                }

            })

        }
    }


    private fun send(submitPostalAddress: SubmitPostalAddress) {
        binding.animationView.visibility = VISIBLE
        coursesViewModel.submitPostalAddress(submitPostalAddress)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}