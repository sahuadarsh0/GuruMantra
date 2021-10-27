package technited.minds.gurumantra.ui.test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import technited.minds.gurumantra.databinding.FragmentBatchDetailsBinding
import technited.minds.gurumantra.databinding.FragmentTestSeriesDetailsBinding
import technited.minds.gurumantra.model.MeetingDetailsItem
import technited.minds.gurumantra.model.Ts
import technited.minds.gurumantra.ui.adapters.MeetingsAdapter
import technited.minds.gurumantra.ui.adapters.TestsAdapter
import technited.minds.gurumantra.ui.batch.BatchDetailsViewModel
import technited.minds.gurumantra.utils.Resource
import technited.minds.gurumantra.utils.SharedPrefs
import javax.inject.Inject

@AndroidEntryPoint
class TestSeriesDetails : Fragment() {

    private val testSeriesViewModel: TestSeriesViewModel by viewModels()
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
        arguments?.getString("id")?.let { testSeriesViewModel.getTestSeriesDetails(it,userSharedPreferences["id"]!!) }
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

    private fun onItemClicked(ts: Ts) {
//        userSharedPreferences["member_id_temp"] = batchDetailsItem.memberId
//        findNavController().navigate(R.id.action_navigation_home_to_navigation_account)
    }
}