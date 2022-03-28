package technited.minds.gurumantra.ui.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import technited.minds.gurumantra.R
import technited.minds.gurumantra.databinding.FragmentLibraryTypeBinding


@AndroidEntryPoint
class LibraryTypeFragment : Fragment() {  // formerly

    private var _binding: FragmentLibraryTypeBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLibraryTypeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.pdf.setOnClickListener {
            openLibraryNotes()
        }
        binding.postal.setOnClickListener {
            openPostalCourse()
        }
        return root
    }

    private fun openPostalCourse() {
        findNavController().navigate(
            R.id.action_navigation_library_type_to_navigation_postal_courses,
        )
    }

    private fun openLibraryNotes() {
        findNavController().navigate(
            R.id.action_navigation_library_type_to_navigation_library_notes,
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}