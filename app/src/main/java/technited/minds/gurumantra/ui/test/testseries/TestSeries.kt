package technited.minds.gurumantra.ui.test.testseries

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import technited.minds.gurumantra.model.Note
import technited.minds.gurumantra.model.TestSeries as TestSeries1
import technited.minds.gurumantra.model.TestSeriesItem
import technited.minds.gurumantra.ui.adapters.TestSeriesAdapter
import technited.minds.gurumantra.utils.Resource


@AndroidEntryPoint
class TestSeries : Fragment() {

    private var _binding: FragmentTestSeriesBinding? = null

    private val testSeriesViewModel: TestSeriesViewModel by viewModels()
    private val testSeriesAdapter = TestSeriesAdapter(this::onItemClicked)
    private val binding get() = _binding!!
    private lateinit var tests: TestSeries1
    private lateinit var type: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTestSeriesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        arguments?.getString("type")?.let { type = it }
        testSeriesViewModel.getTestSeries(type)
        binding.search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (!s.isNullOrEmpty()) {
                    setupSearch(s.toString())
                }
            }
        })

        setupRecyclerView()
        setupObservers()
        return root
    }

    fun setupSearch(strTyped: String) {
        val filteredList = arrayListOf<TestSeriesItem>()

        for (test in tests) {
            if (test.tsName.contains(strTyped)) {
                filteredList.add(test)
            }
            else if (test.tsName.lowercase().contains(strTyped.lowercase())) {
                filteredList.add(test)
            }
        }
        testSeriesAdapter.submitList(filteredList)
    }

    private fun setupRecyclerView() {
        binding.testSeriesList.adapter = testSeriesAdapter
    }

    private fun setupObservers() {
        binding.animationView.visibility = View.VISIBLE
        binding.testSeriesList.visibility = View.GONE

        testSeriesViewModel.testSeries.observe(viewLifecycleOwner, {
            when (it.status) {
                Resource.Status.LOADING -> {
                    binding.animationView.visibility = View.VISIBLE
                    binding.testSeriesList.visibility = View.GONE

                }
                Resource.Status.SUCCESS -> {
                    tests = it.data!!

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
            R.id.action_testSeries_to_testSeriesDetails,
            bundleOf(
                "id" to testSeriesItem.tsId.toString(),
                "type" to type
            )
        )
    }
}