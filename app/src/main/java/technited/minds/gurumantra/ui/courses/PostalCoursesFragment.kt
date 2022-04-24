package technited.minds.gurumantra.ui.courses

import android.os.Bundle
import android.util.Log
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
import technited.minds.gurumantra.databinding.FragmentPostalCoursesBinding
import technited.minds.gurumantra.model.PostalCourseItem
import technited.minds.gurumantra.ui.adapters.PostalCoursesAdapter
import technited.minds.gurumantra.ui.adapters.PostalOrdersAdapter
import technited.minds.gurumantra.utils.Resource
import technited.minds.gurumantra.utils.SharedPrefs
import javax.inject.Inject

@AndroidEntryPoint
class PostalCoursesFragment : Fragment() {

    private var _binding: FragmentPostalCoursesBinding? = null

    @Inject
    lateinit var userSharedPreferences: SharedPrefs
    private val coursesViewModel: CoursesViewModel by viewModels()
    private val postalCoursesAdapter by lazy { PostalCoursesAdapter(this::onItemClicked) }
    private val postalOrdersAdapter = PostalOrdersAdapter()
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPostalCoursesBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setupRecyclerView()
        setupObservers()
        coursesViewModel.getPostalOrders(userSharedPreferences["id"]!!)
        return root
    }


    private fun setupRecyclerView() {
        binding.coursesList.adapter = postalCoursesAdapter
        binding.ordersList.adapter = postalOrdersAdapter
    }

    private fun setupObservers() {
        binding.animationView.visibility = View.VISIBLE
        binding.coursesList.visibility = View.GONE

        coursesViewModel.postalCourses.observe(viewLifecycleOwner, {
            when (it.status) {
                Resource.Status.LOADING -> {
                    binding.animationView.visibility = View.VISIBLE
                    binding.coursesList.visibility = View.GONE

                }
                Resource.Status.SUCCESS -> {
                    val courses = it.data

                    if (courses != null) {

                        postalCoursesAdapter.submitList(courses.postalCourse)
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
        })
        coursesViewModel.postalOrders.observe(viewLifecycleOwner, {
            when (it.status) {
                Resource.Status.LOADING -> {
                    binding.animationView.visibility = View.VISIBLE

                }
                Resource.Status.SUCCESS -> {
                    val courses = it.data

                    if (courses != null) {
                        if (courses.pcs.isNotEmpty()) {
                            binding.orderGroup.visibility = View.VISIBLE
                            postalOrdersAdapter.submitList(courses.pcs)
                            binding.ordersList.visibility = View.VISIBLE
                            binding.animationView.visibility = View.GONE

                        }
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

                }

            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onItemClicked(postal: PostalCourseItem) {
        findNavController().navigate(
            R.id.action_navigation_postal_courses_to_postalAddressFragment,
            bundleOf("id" to postal.pcsId.toString())
        )
    }
}