package technited.minds.gurumantra.ui.courses

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItems
import dagger.hilt.android.AndroidEntryPoint
import technited.minds.gurumantra.R
import technited.minds.gurumantra.databinding.FragmentCoursesDetailsBinding
import technited.minds.gurumantra.model.Module
import technited.minds.gurumantra.ui.adapters.ModulesAdapter
import technited.minds.gurumantra.ui.payment.PaymentPage
import technited.minds.gurumantra.ui.payment.PaymentViewModel
import technited.minds.gurumantra.utils.Resource
import technited.minds.gurumantra.utils.SharedPrefs
import javax.inject.Inject

@AndroidEntryPoint
class CourseDetails : Fragment() {

    private val coursesViewModel: CoursesViewModel by viewModels()
    private val paymentViewModel: PaymentViewModel by viewModels()
    private var _binding: FragmentCoursesDetailsBinding? = null
    private val modulesAdapter = ModulesAdapter(this::onItemClicked)
    private val binding get() = _binding!!
    private var courseEnrolls: Int = 0
    private var courseId: Int = 0

    @Inject
    lateinit var userSharedPreferences: SharedPrefs

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCoursesDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setupRecyclerView()
        setupObservers()
        arguments?.getString("id")?.let { coursesViewModel.getCourseDetails(userSharedPreferences["id"]!!, it) }
        binding.startCourse.setOnClickListener {
            startCourse()
        }
        return root
    }

    private fun setupRecyclerView() {
        binding.modulesList.adapter = modulesAdapter
    }

    private fun setupObservers() {
        binding.animationView.visibility = VISIBLE
        binding.modulesList.visibility = GONE

        coursesViewModel.courseDetails.observe(viewLifecycleOwner, {
            when (it.status) {
                Resource.Status.LOADING -> {
                    binding.animationView.visibility = VISIBLE
                    binding.modulesList.visibility = GONE

                }
                Resource.Status.SUCCESS -> {
                    val details = it.data

                    if (details != null) {
                        if (details.user != null)
                            userSharedPreferences["package"] = details.user.packageX.toString()
                        binding.details = details.courses
                        binding.animationView.visibility = GONE

                        modulesAdapter.submitList(details.modules)
                        binding.animationView.visibility = GONE
                        courseEnrolls = details.enrolls.toInt()
                        courseId = details.courses.courseId
                        if (courseEnrolls == 1) {
                            binding.startCourse.visibility = GONE
                            binding.modulesList.visibility = VISIBLE
                        } else {
                            binding.startCourse.visibility = VISIBLE
                            binding.modulesList.visibility = GONE
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
                    binding.animationView.visibility = GONE
                    binding.modulesList.visibility = VISIBLE
                }

            }
        })

        coursesViewModel.enroll.observe(viewLifecycleOwner, {
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
                            i.putExtra("price", details.course.pid.toString())
                            i.putExtra("title", details.data.name)
                            i.putExtra("orderId", details.data.orderId)
                            i.putExtra("type", "course")
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

    private fun onItemClicked(module: Module) {
        var myItems = listOf<String>()
        module.lectures.forEach { myItems = myItems.plusElement(it.lectureTitle) }
        MaterialDialog(requireActivity()).show {
            title(text = module.moduleTitle)
            cornerRadius(16f)
            listItems(items = myItems) { dialog, index, text ->
                val action = CourseDetailsDirections.actionCoursesDetailsToPlay(module.lectures[index].lectureVideo)
                NavHostFragment.findNavController(this@CourseDetails).navigate(action)
            }


            negativeButton(text = "cancel") { dialog ->
                dialog.dismiss()
            }
        }
    }

    private fun openPackage() {
        findNavController().navigate(R.id.action_coursesDetails_to_navigation_packages)
    }

    private fun startCourse() {
        if (courseEnrolls == 1) {
            startLectures()
        } else {
            when (binding.details?.pid) {
                1 -> {
                    coursesViewModel.getEnrolled(userSharedPreferences["id"]!!, courseId.toString(), "course")
                    startLectures()
                }
                2 -> {
                    if (userSharedPreferences["package"]!!.toInt() == 2) {
                        coursesViewModel.getEnrolled(userSharedPreferences["id"]!!, courseId.toString(), "course")
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
                    paymentViewModel.getPaymentData(userSharedPreferences["id"]!!, courseId.toString(), "course")
                }
            }
        }
    }

    private fun startLectures() {

    }
}