package technited.minds.gurumantra.ui.live

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import dagger.hilt.android.AndroidEntryPoint
import technited.minds.gurumantra.R
import technited.minds.gurumantra.databinding.FragmentBatchDetailsBinding
import technited.minds.gurumantra.model.MeetingDetailsItem
import technited.minds.gurumantra.ui.adapters.MeetingsAdapter
import technited.minds.gurumantra.ui.payment.PaymentViewModel
import technited.minds.gurumantra.utils.Resource
import technited.minds.gurumantra.utils.SharedPrefs
import javax.inject.Inject

@AndroidEntryPoint
class BatchDetails : Fragment() {

    private val batchDetailsViewModel: BatchDetailsViewModel by viewModels()
    private val paymentViewModel: PaymentViewModel by viewModels()
    private var _binding: FragmentBatchDetailsBinding? = null
    private val meetingsAdapter = MeetingsAdapter(this::onItemClicked)
    private val binding get() = _binding!!
    private val type = "batch"
    private lateinit var batchId: String
    private var batchType: Int = 0
    private var batchEnrolled: Int = 0

    @Inject
    lateinit var userSharedPreferences: SharedPrefs

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBatchDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setupRecyclerView()
        setupObservers()
        batchId = arguments?.getString("id")!!
        batchType = arguments?.getInt("type")!!
        batchDetailsViewModel.getBatchDetails(userSharedPreferences["id"]!!, batchId)
        batchDetailsViewModel.getMeetings(batchId, batchType)

        return root
    }

    private fun setupRecyclerView() {
        binding.meetingsList.adapter = meetingsAdapter
    }

    private fun setupObservers() {
        binding.animationView.visibility = VISIBLE
        binding.meetingsList.visibility = GONE

        batchDetailsViewModel.meetings.observe(viewLifecycleOwner, {
            when (it.status) {
                Resource.Status.LOADING -> {
                    binding.animationView.visibility = VISIBLE
                    binding.meetingsList.visibility = GONE

                }
                Resource.Status.SUCCESS -> {
                    val meetings = it.data

                    if (meetings != null) {

                        meetingsAdapter.submitList(meetings.details)
                        binding.animationView.visibility = GONE
                        binding.meetingsList.visibility = VISIBLE

                    }

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
                    binding.animationView.visibility = GONE
                    binding.meetingsList.visibility = VISIBLE
                }

            }
        })
        batchDetailsViewModel.batchDetails.observe(viewLifecycleOwner, {
            when (it.status) {
                Resource.Status.LOADING -> {
                    binding.animationView.visibility = VISIBLE

                }
                Resource.Status.SUCCESS -> {
                    val details = it.data

                    if (details != null) {

                        binding.details = details.batch
                        batchEnrolled = details.batchEnrolled
                        binding.animationView.visibility = GONE

                    }

                }
                Resource.Status.ERROR -> {
                    binding.animationView.visibility = GONE

                }

            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onItemClicked(meetingDetailsItem: MeetingDetailsItem, buttonType: String) {
        if (batchEnrolled == 1) {
            startLiveActivity(meetingDetailsItem.classId.toString(), buttonType)

        } else {
            when (binding.details?.batchPackage) {
                1 -> {
                    batchDetailsViewModel.getEnrolled(
                        userSharedPreferences["id"]!!,
                        batchId,
                        type
                    )
                    startLiveActivity(meetingDetailsItem.classId.toString(), buttonType)
                }
                2 -> {
                    if (userSharedPreferences["package"]!!.toInt() == 2) {
                        batchDetailsViewModel.getEnrolled(
                            userSharedPreferences["id"]!!,
                            batchId,
                            type
                        )
                        Toast.makeText(requireActivity(), "Enrolling to this course", Toast.LENGTH_SHORT).show()
                    } else
                        MaterialDialog(requireContext()).show {
                            title(text = "Not Enrolled")
                            message(R.string.enroll_message)
                            cornerRadius(16f)
                            positiveButton(text = "OK") { dialog ->
                                dialog.dismiss()
                                openPackage()
                            }
                        }

                }
                3 -> {
                    val dialog: MaterialDialog = MaterialDialog(requireContext()).show {
                        title(text = "Apply Coupons ?")
                        message(text = "Enter coupon code")
                        cornerRadius(16f)
                        input(allowEmpty = true) { dialog, text ->
//                            val coupon: EditText = dialog.getInputField()
                            paymentViewModel.getPaymentData(
                                userSharedPreferences["id"]!!,
                                batchId,
                                type,
                                text.toString()
                            )
                        }
                        positiveButton(text = "OK") { dialog ->
                            dialog.dismiss()
                        }
                        negativeButton(text = "No Coupons") { dialog ->
                            paymentViewModel.getPaymentData(
                                userSharedPreferences["id"]!!,
                                batchId,
                                type
                            )
                        }
                    }
                }
            }
        }
    }

    private fun startLiveActivity(id: String, buttonType: String) {
        when (buttonType) {

            "join" -> when (batchType) { // Join Button
                0 -> {
                    val action = BatchDetailsDirections.actionBatchDetailsToZoomActivity(id) // Zoom Conference
                    findNavController().navigate(action)
                }
                1 -> {
                    val action = BatchDetailsDirections.actionBatchDetailsToPlayNComments(id,"live","live") //Youtube Live
                    findNavController().navigate(action)
                }
            }
            "previous" -> {
                findNavController().navigate(
                    R.id.action_batchDetails_to_previousClassFragment,
                    bundleOf(
                        "id" to id,
                        "type" to batchType
                    )
                )
            }
        }
    }

    private fun openPackage() {
        findNavController().navigate(R.id.action_batchDetails_to_navigation_packages)
    }
}