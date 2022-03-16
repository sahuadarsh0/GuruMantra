package technited.minds.gurumantra.ui.test.testseries

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.getInputField
import com.afollestad.materialdialogs.input.input
import com.rajat.pdfviewer.PdfViewerActivity
import dagger.hilt.android.AndroidEntryPoint
import technited.minds.gurumantra.R
import technited.minds.gurumantra.databinding.FragmentTestSeriesDetailsBinding
import technited.minds.gurumantra.model.Ts
import technited.minds.gurumantra.ui.WebPage
import technited.minds.gurumantra.ui.adapters.TestsAdapter
import technited.minds.gurumantra.ui.payment.PaymentPage
import technited.minds.gurumantra.ui.payment.PaymentViewModel
import technited.minds.gurumantra.ui.test.ExamActivity
import technited.minds.gurumantra.utils.Constants
import technited.minds.gurumantra.utils.Resource
import technited.minds.gurumantra.utils.SharedPrefs
import javax.inject.Inject

@AndroidEntryPoint
class TestSeriesDetails : Fragment() {

    private val testSeriesViewModel: TestSeriesViewModel by viewModels()
    private val paymentViewModel: PaymentViewModel by viewModels()
    private var _binding: FragmentTestSeriesDetailsBinding? = null
    private val testsAdapter = TestsAdapter(this::onItemClicked)
    private val binding get() = _binding!!
    private lateinit var type: String
    private var tsEnrolls: Int = 0

    @Inject
    lateinit var userSharedPreferences: SharedPrefs

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTestSeriesDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setupRecyclerView()
        setupObservers()
        arguments?.getString("type")?.let { type = it }

        arguments?.getString("id")?.let {
            testSeriesViewModel.getTestSeriesDetails(userSharedPreferences["id"]!!, it, type)
            testSeriesViewModel.getListTests(it, type)
        }

        return root
    }

    private fun setupRecyclerView() {
        binding.testSeriesList.adapter = testsAdapter
    }

    private fun setupObservers() {
        binding.animationView.visibility = VISIBLE
        binding.testSeriesList.visibility = GONE

        testSeriesViewModel.tests.observe(viewLifecycleOwner, {
            when (it.status) {
                Resource.Status.LOADING -> {
                    binding.animationView.visibility = VISIBLE
                    binding.testSeriesList.visibility = GONE

                }
                Resource.Status.SUCCESS -> {
                    val tests = it.data

                    if (tests != null) {

                        testsAdapter.submitList(tests.ts)
                        binding.details = tests.tss
                        binding.animationView.visibility = GONE
                        binding.testSeriesList.visibility = VISIBLE

                    }

                }
                Resource.Status.ERROR -> {
                    binding.animationView.visibility = GONE
                    binding.testSeriesList.visibility = VISIBLE
                }

            }
        })

        testSeriesViewModel.testSeriesDetails.observe(viewLifecycleOwner, {
            when (it.status) {
                Resource.Status.LOADING -> {
                    binding.animationView.visibility = VISIBLE

                }
                Resource.Status.SUCCESS -> {
                    val details = it.data

                    if (details != null) {
                        if (type != "pdf")
                            binding.details = details.tss
                        userSharedPreferences["package"] = details.user?.packageX.toString()
                        binding.animationView.visibility = GONE
                        tsEnrolls = details.tsEnrols

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

                }

            }
        })

        testSeriesViewModel.enroll.observe(viewLifecycleOwner, {
            when (it.status) {
                Resource.Status.LOADING -> {
                    binding.animationView.visibility = VISIBLE

                }
                Resource.Status.SUCCESS -> {
                    val details = it.data

                    if (details != null) {
                        if (details.status == 1)
                            Toast.makeText(requireContext(), details.message, Toast.LENGTH_SHORT).show()
                        binding.animationView.visibility = GONE

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

                }

            }
        })

        paymentViewModel.payment.observe(viewLifecycleOwner, {
            when (it.status) {
                Resource.Status.LOADING -> {
                    binding.animationView.visibility = VISIBLE

                }
                Resource.Status.SUCCESS -> {
                    val details = it.data

                    if (details != null) {
                        if (details.status == 1) {
                            Toast.makeText(requireContext(), details.message, Toast.LENGTH_SHORT).show()

                            val i = Intent(activity, PaymentPage::class.java)
                            i.putExtra("price", details.tss.price.toString())
                            i.putExtra("title", details.data.name)
                            i.putExtra("orderId", details.data.orderId)
                            i.putExtra("type", type)
                            startActivity(i)
                        }
                    }
                    binding.animationView.visibility = GONE

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

                }

            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onItemClicked(ts: Ts) {
//        val i = Intent(activity, ExamActivity::class.java)
//        i.putExtra("id", ts.tId.toString())
//        i.putExtra("type", type)
        if (type == "pdf")
            startActivity(
                PdfViewerActivity.launchPdfFromUrl(           //PdfViewerActivity.Companion.launchPdfFromUrl(..   :: incase of JAVA
                    context,
                    Constants.URL.toString() + ts.ptQuestions,                                // PDF URL in String format
                    ts.tName,                        // PDF Name/Title in String format
                    "",                  // If nothing specific, Put "" it will save to Downloads
                    enableDownload = false           // This param is true by default.
                )
            )
        if (tsEnrolls == 1) {
//            startActivity(i)
            startWebActivity(userSharedPreferences["id"]!!, ts.tId.toString(), type)

        } else {
            when (binding.details?.packageX) {
                1 -> {
                    testSeriesViewModel.getEnrolled(userSharedPreferences["id"]!!, ts.tsId.toString(), type)
                    startWebActivity(userSharedPreferences["id"]!!, ts.tId.toString(), type)
                }
                2 -> {
                    if (userSharedPreferences["package"]!!.toInt() == 2) {
                        testSeriesViewModel.getEnrolled(userSharedPreferences["id"]!!, ts.tsId.toString(), type)
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
                        title(text = "Apply Coupons")
                        message(text = "Do you have coupon code ?")
                        cornerRadius(16f)
                        input(allowEmpty = true) { dialog, text ->


//                            val coupon: EditText = dialog.getInputField()
                            paymentViewModel.getPaymentData(
                                userSharedPreferences["id"]!!,
                                ts.tsId.toString(),
                                type,
                                text.toString()
                            )
                        }
                        positiveButton(text = "OK") { dialog ->
                            dialog.dismiss()
                        }
                    }
                }
            }
        }
    }

    private fun openPackage() {
        findNavController().navigate(R.id.action_testSeriesDetails_to_navigation_packages)
    }

    private fun startWebActivity(usId: String, tsId: String, type: String) {
        val intent = Intent(requireContext(), WebPage::class.java)
        when (type) {
            "test" -> intent.putExtra("url", "https://gurumantra.online/api/webTestsDetails?testId=$tsId&userId=$usId")
            "practice" -> intent.putExtra("url", "https://gurumantra.online/api/webStartSet?psId=$tsId")
        }
        startActivity(intent)
    }

}