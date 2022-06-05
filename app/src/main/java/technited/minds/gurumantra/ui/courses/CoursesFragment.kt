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
import technited.minds.gurumantra.databinding.FragmentCoursesBinding
import technited.minds.gurumantra.model.Course
import technited.minds.gurumantra.ui.adapters.CoursesAdapter
import technited.minds.gurumantra.utils.Resource
import technited.minds.gurumantra.utils.SharedPrefs
import javax.inject.Inject

@AndroidEntryPoint
class CoursesFragment : Fragment() {

    private var _binding: FragmentCoursesBinding? = null

    @Inject
    lateinit var userSharedPreferences: SharedPrefs
    private val coursesViewModel: CoursesViewModel by viewModels()
    private val coursesAdapter = CoursesAdapter(this::onItemClicked)
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCoursesBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setupRecyclerView()
        setupObservers()
        return root
    }


    private fun setupRecyclerView() {
        binding.coursesList.adapter = coursesAdapter
    }

    private fun setupObservers() {
        binding.animationView.visibility = View.VISIBLE
        binding.coursesList.visibility = View.GONE

        coursesViewModel.courses.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    binding.animationView.visibility = View.VISIBLE
                    binding.coursesList.visibility = View.GONE

                }
                Resource.Status.SUCCESS -> {
                    val courses = it.data

                    if (courses != null) {

                        coursesAdapter.submitList(courses.courses)
                        binding.animationView.visibility = View.GONE
                        binding.coursesList.visibility = View.VISIBLE
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
                    binding.coursesList.visibility = View.VISIBLE

                }

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onItemClicked(course: Course) {
        findNavController().navigate(
            R.id.action_navigation_courses_to_coursesDetails,
            bundleOf("id" to course.courseId.toString())
        )
    }
}