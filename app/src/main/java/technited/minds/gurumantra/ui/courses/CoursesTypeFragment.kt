package technited.minds.gurumantra.ui.courses

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
import technited.minds.gurumantra.databinding.FragmentCoursesTypeBinding
import technited.minds.gurumantra.databinding.FragmentTestSeriesBinding
import technited.minds.gurumantra.databinding.FragmentTestSeriesTypeBinding
import technited.minds.gurumantra.model.TestSeriesItem
import technited.minds.gurumantra.ui.adapters.TestSeriesAdapter
import technited.minds.gurumantra.utils.Resource


@AndroidEntryPoint
class CoursesTypeFragment : Fragment() {

    private var _binding: FragmentCoursesTypeBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCoursesTypeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.video.setOnClickListener{
            openVideoCourse()
        }
        binding.postal.setOnClickListener{
            openPostalCourse()
        }
        return root
    }

    private fun openPostalCourse() {
//        findNavController().navigate(
//            R.id.action_navigation_courses_type_to_navigation_postal_courses,
//        )
    }

    private fun openVideoCourse() {
        findNavController().navigate(
            R.id.action_navigation_courses_type_to_navigation_courses,
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}