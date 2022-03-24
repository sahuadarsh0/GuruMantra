package technited.minds.gurumantra.ui.live

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
import technited.minds.gurumantra.databinding.FragmentLiveClassTypeBinding
import technited.minds.gurumantra.databinding.FragmentTestSeriesBinding
import technited.minds.gurumantra.databinding.FragmentTestSeriesTypeBinding
import technited.minds.gurumantra.model.TestSeriesItem
import technited.minds.gurumantra.ui.adapters.TestSeriesAdapter
import technited.minds.gurumantra.utils.Resource


@AndroidEntryPoint
class LiveClassType : Fragment() {

    private var _binding: FragmentLiveClassTypeBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLiveClassTypeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.zoom.setOnClickListener{
            openLiveClass("conference")
        }
        binding.youtube.setOnClickListener{
            openLiveClass("live")
        }
        return root
    }

    private fun openLiveClass(type: String) {
        findNavController().navigate(
            R.id.action_navigation_live_class_type_to_navigation_live_class,
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