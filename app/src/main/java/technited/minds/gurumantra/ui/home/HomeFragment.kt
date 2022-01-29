package technited.minds.gurumantra.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import dagger.hilt.android.AndroidEntryPoint
import technited.minds.gurumantra.R
import technited.minds.gurumantra.databinding.FragmentHomeBinding
import technited.minds.gurumantra.model.BatchDetailsItem
import technited.minds.gurumantra.model.Blog
import technited.minds.gurumantra.model.TestSeriesItem
import technited.minds.gurumantra.ui.adapters.BatchesAdapter
import technited.minds.gurumantra.ui.adapters.BlogsAdapter
import technited.minds.gurumantra.ui.adapters.TestSeriesAdapter
import technited.minds.gurumantra.utils.Constants
import technited.minds.gurumantra.utils.Resource

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val homeViewModel: HomeViewModel by viewModels()
    private val blogsAdapter = BlogsAdapter(this::onBlogClicked)
    private val testSeriesAdapter = TestSeriesAdapter(this::onTestClicked)
    private val practiceSetsAdapter = TestSeriesAdapter(this::onPracticeClicked)
    private val pdfSetsAdapter = TestSeriesAdapter(this::onPdfClicked)
    private val batchesAdapter = BatchesAdapter(this::onBatchClicked)
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setupRecyclerView()
        setupObservers()
        return root
    }

    private fun setupRecyclerView() {
        binding.blogsList.adapter = blogsAdapter
        binding.testSeriesList.adapter = testSeriesAdapter
        binding.practiceSetsList.adapter = practiceSetsAdapter
        binding.pdfSetsList.adapter = pdfSetsAdapter
        binding.batchesList.adapter = batchesAdapter
    }

    private fun setupObservers() {
        binding.animationView.visibility = View.VISIBLE
        binding.group.visibility = View.GONE
        val imageList = ArrayList<SlideModel>()
        val smallImageList = ArrayList<SlideModel>()

        homeViewModel.home.observe(viewLifecycleOwner, {
            when (it.status) {
                Resource.Status.LOADING -> {
                    binding.animationView.visibility = View.VISIBLE
                    binding.group.visibility = View.GONE

                }
                Resource.Status.SUCCESS -> {
                    val home = it.data

                    if (home != null) {
                        for ((i, a) in home.sliders.withIndex()) {
                            imageList.add(SlideModel(Constants.URL.toString() + a.sliderImage))
                        }
                        for ((i, v) in home.smallSliders.withIndex()) {
                            smallImageList.add(SlideModel(Constants.URL.toString() + v.sliderImage))
                        }
                        binding.slider.setImageList(imageList, ScaleTypes.CENTER_CROP)
                        binding.smallSlider.setImageList(smallImageList, ScaleTypes.CENTER_CROP)

                        blogsAdapter.submitList(home.blogs.subList(0,3))
                        testSeriesAdapter.submitList(home.tss)
                        practiceSetsAdapter.submitList(home.practs)
                        pdfSetsAdapter.submitList(home.pdfts)
                        batchesAdapter.submitList(home.batches)
                        binding.marqueeText.text = home.notices.joinToString { notice ->"               * "+notice.content }
                        binding.marqueeText.isSelected = true
                        binding.animationView.visibility = View.GONE
                        binding.group.visibility = View.VISIBLE

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
                    binding.group.visibility = View.VISIBLE

                }

            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onBlogClicked(blog: Blog) {
        findNavController().navigate(
            R.id.action_navigation_home_to_blogDetails,
            bundleOf("id" to blog.blogId.toString())
        )
    }

    private fun onTestClicked(testSeriesItem: TestSeriesItem) {
        findNavController().navigate(
            R.id.action_navigation_home_to_testSeriesDetails,
            bundleOf(
                "id" to testSeriesItem.tsId.toString(),
                "type" to "test"
            )
        )
    }

    private fun onPracticeClicked(testSeriesItem: TestSeriesItem) {
        findNavController().navigate(
            R.id.action_navigation_home_to_testSeriesDetails,
            bundleOf(
                "id" to testSeriesItem.tsId.toString(),
                "type" to "practice"
            )
        )
    }
    private fun onPdfClicked(testSeriesItem: TestSeriesItem) {
        findNavController().navigate(
            R.id.action_navigation_home_to_testSeriesDetails,
            bundleOf(
                "id" to testSeriesItem.tsId.toString(),
                "type" to "pdf"
            )
        )
    }

    private fun onBatchClicked(batchDetailsItem: BatchDetailsItem) {
        findNavController().navigate(
            R.id.action_navigation_home_to_batchDetails,
            bundleOf("id" to batchDetailsItem.batchId.toString())
        )
    }
}