package technited.minds.gurumantra.ui.blogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import technited.minds.gurumantra.R
import technited.minds.gurumantra.databinding.FragmentBlogsBinding
import technited.minds.gurumantra.model.Blog
import technited.minds.gurumantra.ui.adapters.BlogsAdapter
import technited.minds.gurumantra.utils.Resource

@AndroidEntryPoint
class BlogsFragment : Fragment() {

    private var _binding: FragmentBlogsBinding? = null

    private val blogsViewModel: BlogsViewModel by viewModels()
    private val blogsAdapter = BlogsAdapter(this::onItemClicked)
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentBlogsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupRecyclerView()
        setupObservers()

        return root
    }

    private fun setupRecyclerView() {
        binding.blogsList.adapter = blogsAdapter
    }

    private fun setupObservers() {
        binding.animationView.visibility = View.VISIBLE
        binding.blogsList.visibility = View.GONE

        blogsViewModel.blogs.observe(viewLifecycleOwner, {
            when (it.status) {
                Resource.Status.LOADING -> {
                    binding.animationView.visibility = View.VISIBLE
                    binding.blogsList.visibility = View.GONE

                }
                Resource.Status.SUCCESS -> {
                    val blogs = it.data

                    if (blogs != null) {

                        blogsAdapter.submitList(blogs)
                        binding.animationView.visibility = View.GONE
                        binding.blogsList.visibility = View.VISIBLE

                    }

                }
                Resource.Status.ERROR -> {
                    binding.animationView.visibility = View.GONE
                    binding.blogsList.visibility = View.VISIBLE

                }

            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onItemClicked(blog: Blog) {
        findNavController().navigate(
            R.id.action_navigation_blogs_to_blogDetails,
            bundleOf("id" to blog.blogId.toString())
        )
    }
}