package technited.minds.gurumantra.ui.test.practice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import dagger.hilt.android.AndroidEntryPoint
import technited.minds.gurumantra.R
import technited.minds.gurumantra.databinding.FragmentTestSeriesBinding
import technited.minds.gurumantra.model.TestSeriesItem
import technited.minds.gurumantra.ui.adapters.TestSeriesAdapter
import technited.minds.gurumantra.utils.Resource


@AndroidEntryPoint
class PracticeSet : Fragment() {

    private var _binding: FragmentTestSeriesBinding? = null

    private val practiceSetViewModel: PracticeSetViewModel by viewModels()
    private val testSeriesAdapter = TestSeriesAdapter(this::onItemClicked)
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTestSeriesBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setupRecyclerView()
        setupObservers()

        return root
    }

    private fun setupRecyclerView() {
        binding.testSeriesList.adapter = testSeriesAdapter
    }

    private fun setupObservers() {
        binding.animationView.visibility = View.VISIBLE
        binding.testSeriesList.visibility = View.GONE

        practiceSetViewModel.setSeries.observe(viewLifecycleOwner, {
            when (it.status) {
                Resource.Status.LOADING -> {
                    binding.animationView.visibility = View.VISIBLE
                    binding.testSeriesList.visibility = View.GONE

                }
                Resource.Status.SUCCESS -> {
                    val tests = it.data

                    if (tests != null) {

                        testSeriesAdapter.submitList(tests)
                        binding.animationView.visibility = View.GONE
                        binding.testSeriesList.visibility = View.VISIBLE

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
                    binding.animationView.visibility = View.GONE
                    binding.testSeriesList.visibility = View.VISIBLE

                }

            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onItemClicked(testSeriesItem: TestSeriesItem) {
        findNavController().navigate(
            R.id.action_navigation_test_series_to_testSeriesDetails,
            bundleOf("id" to testSeriesItem.tsId.toString())
        )
    }
}