package technited.minds.gurumantra.ui.test.testseries

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import dagger.hilt.android.AndroidEntryPoint
import technited.minds.gurumantra.R
import technited.minds.gurumantra.databinding.FragmentTestSeriesDetailsBinding
import technited.minds.gurumantra.model.Ts
import technited.minds.gurumantra.ui.adapters.TestsAdapter
import technited.minds.gurumantra.ui.payment.PaymentPage
import technited.minds.gurumantra.ui.payment.PaymentViewModel
import technited.minds.gurumantra.ui.test.ExamActivity
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
        arguments?.getString("id")?.let { testSeriesViewModel.getTestSeriesDetails(it, userSharedPreferences["id"]!!) }
        arguments?.getString("id")?.let { testSeriesViewModel.getListTests(it) }

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

                        binding.details = details.tss
//                        userSharedPreferences["package"] = details.user.packageX.toString()
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
                            i.putExtra("id", details.tss.tsId)
                            i.putExtra("price", details.tss.price.toString())
                            i.putExtra("title", details.data.name)
                            i.putExtra("orderId", details.data.orderId)
                            i.putExtra("type", "series")
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
        val i = Intent(activity, ExamActivity::class.java)
        i.putExtra("id", ts.tId.toString())

        if (binding.details?.tsEnrolls == 1) {
            startActivity(i)
        } else {
            when (binding.details?.packageX) {
                1 -> {
                    testSeriesViewModel.getEnrolled(ts.tsId.toString(), userSharedPreferences["id"]!!)
                    startActivity(i)
                }
                2 -> {
                    if (userSharedPreferences["package"]!!.toInt() == 2) {
                        testSeriesViewModel.getEnrolled(ts.tsId.toString(), userSharedPreferences["id"]!!)
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
                    paymentViewModel.getPaymentData(userSharedPreferences["id"]!!, ts.tsId.toString(), "series")
                }
            }
        }
    }

    private fun openPackage() {
        findNavController().navigate(R.id.action_testSeriesDetails_to_navigation_packages)
    }

}