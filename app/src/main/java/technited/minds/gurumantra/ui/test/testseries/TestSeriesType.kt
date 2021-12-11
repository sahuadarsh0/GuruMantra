package technited.minds.gurumantra.ui.test.testseries

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
import technited.minds.gurumantra.databinding.FragmentTestSeriesTypeBinding
import technited.minds.gurumantra.model.TestSeriesItem
import technited.minds.gurumantra.ui.adapters.TestSeriesAdapter
import technited.minds.gurumantra.utils.Resource


@AndroidEntryPoint
class TestSeriesType : Fragment() {

    private var _binding: FragmentTestSeriesTypeBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTestSeriesTypeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.pdf.setOnClickListener{
            openTestSeries("pdf")
        }
        binding.test.setOnClickListener{
            openTestSeries("test")
        }
        binding.practice.setOnClickListener{
            openTestSeries("practice")
        }
        return root
    }

    private fun openTestSeries(type: String) {
        findNavController().navigate(
            R.id.action_navigation_test_series_type_to_testSeries,
            bundleOf(
                "type" to type
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}