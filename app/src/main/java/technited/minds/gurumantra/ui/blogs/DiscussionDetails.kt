package technited.minds.gurumantra.ui.blogs

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.afollestad.materialdialogs.MaterialDialog
import dagger.hilt.android.AndroidEntryPoint
import technited.minds.gurumantra.R
import technited.minds.gurumantra.data.local.BlogsDao
import technited.minds.gurumantra.databinding.FragmentBlogDetailsBinding
import technited.minds.gurumantra.databinding.FragmentDiscussionDetailsBinding
import technited.minds.gurumantra.model.Blog
import technited.minds.gurumantra.model.Comment
import technited.minds.gurumantra.ui.adapters.CommentsAdapter
import technited.minds.gurumantra.utils.Resource
import technited.minds.gurumantra.utils.SharedPrefs
import javax.inject.Inject

@AndroidEntryPoint
class DiscussionDetails : Fragment() {

    private var _binding: FragmentDiscussionDetailsBinding? = null
    private val binding get() = _binding!!
    private val commentsAdapter = CommentsAdapter(this::onItemClicked)

    private val commentsViewModel: CommentsViewModel by viewModels()
    private val blogsViewModel: BlogsViewModel by viewModels()
    private lateinit var dId: String

    @Inject
    lateinit var userSharedPreferences: SharedPrefs


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDiscussionDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setupObservers()
        setupRecyclerView()
        binding.postButton.setOnClickListener {
            if (binding.postComment.text.isNotEmpty()) {
                commentsViewModel.postComment(
                    userSharedPreferences["id"]!!.toInt(),
                    dId.toInt(),
                    binding.postComment.text.toString(),
                    "discussion"
                )
            }
        }

        return root
    }

    private fun setupRecyclerView() {
        binding.commentsList.adapter = commentsAdapter
    }


    private fun setupObservers() {
        binding.animationView.visibility = VISIBLE
        val id = arguments?.getString("id")
        if (id != null) {
            dId = id
            blogsViewModel.getDiscussionForumDetail(dId)
            commentsViewModel.getComments(dId,"discussion")
        }
        commentsViewModel.comment.observe(viewLifecycleOwner, {
            when (it.status) {
                Resource.Status.LOADING -> {
                    binding.animationView.visibility = VISIBLE

                }
                Resource.Status.SUCCESS -> {
                    val dcs = it.data

                    if (dcs != null) {

                        binding.commentsList.visibility = VISIBLE
                        commentsAdapter.submitList(dcs.comment)
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

        commentsViewModel.response.observe(viewLifecycleOwner, {
            if (it.data != null) {
                if (it.data.data == 1) {
                    Toast.makeText(requireContext(), "Comment Posted Successfully", Toast.LENGTH_SHORT).show()
                    binding.postComment.setText("")
                    commentsViewModel.getComments(dId,"discussion")
                }
            }
        })
        blogsViewModel.dcs.observe(viewLifecycleOwner, {
            if (it.data != null) {
                binding.discussion = it.data.dcs
                binding.animationView.visibility = GONE
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