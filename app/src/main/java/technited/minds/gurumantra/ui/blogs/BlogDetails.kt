package technited.minds.gurumantra.ui.blogs

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import technited.minds.gurumantra.R
import technited.minds.gurumantra.data.local.BlogsDao
import technited.minds.gurumantra.databinding.FragmentBlogDetailsBinding
import technited.minds.gurumantra.model.Blog
import technited.minds.gurumantra.model.Comment
import technited.minds.gurumantra.ui.adapters.CommentsAdapter
import technited.minds.gurumantra.utils.Resource
import javax.inject.Inject

@AndroidEntryPoint
class BlogDetails : Fragment() {

    private var _binding: FragmentBlogDetailsBinding? = null
    private val binding get() = _binding!!
    private val commentsAdapter = CommentsAdapter(this::onItemClicked)

    private val blogsViewModel: BlogsViewModel by viewModels()


    @Inject
    lateinit var localBlogs: BlogsDao

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBlogDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setupObservers()
        setupRecyclerView()
        return root
    }

    private fun setupRecyclerView() {
        binding.commentsList.adapter = commentsAdapter
    }


    private fun setupObservers() {
        binding.animationView.visibility = VISIBLE
        val id = arguments?.getString("id")
        if (id != null) {
            localBlogs.getBlog(id).observe(viewLifecycleOwner, {
                binding.blog = it
                binding.animationView.visibility = GONE
            })

            blogsViewModel.getComments(id)
        }
        blogsViewModel.comment.observe(viewLifecycleOwner, {
            when (it.status) {
                Resource.Status.LOADING -> {
                    binding.animationView.visibility = VISIBLE

                }
                Resource.Status.SUCCESS -> {
                    val comments = it.data

                    if (comments != null) {

                        binding.commentsList.visibility = VISIBLE
                        commentsAdapter.submitList(comments)
                        binding.animationView.visibility = GONE

                    }
                }
                    Resource.Status.ERROR -> {
                        binding.animationView.visibility = GONE

                    }
                }
            })


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onItemClicked(comment: Comment) {
    }
}