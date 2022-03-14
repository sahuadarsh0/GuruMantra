package technited.minds.gurumantra.ui.blogs

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import technited.minds.gurumantra.databinding.FragmentBlogsBinding
import technited.minds.gurumantra.model.Blog
import technited.minds.gurumantra.model.Dcs
import technited.minds.gurumantra.model.GetBlogs
import technited.minds.gurumantra.ui.WebPage
import technited.minds.gurumantra.ui.adapters.BlogsAdapter
import technited.minds.gurumantra.ui.adapters.DiscussionAdapter
import technited.minds.gurumantra.utils.Resource

@AndroidEntryPoint
class BlogsFragment : Fragment() {

    private var _binding: FragmentBlogsBinding? = null

    private val blogsViewModel: BlogsViewModel by viewModels()
    private val blogsAdapter = BlogsAdapter(this::onItemClicked)
    private val discussionAdapter = DiscussionAdapter(this::onItemClicked)
    private val binding get() = _binding!!
    private lateinit var  blogs : List<Blog>


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentBlogsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupRecyclerView()
        setupObservers()

        binding.searchBlog.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (!s.isNullOrEmpty()) {
                    filter(s.toString())
                }
            }
        })

        return root
    }

    private fun setupRecyclerView() {
        binding.blogsList.adapter = blogsAdapter
        binding.discussionList.adapter = discussionAdapter
    }


    fun filter(strTyped: String) {
        val filteredList = arrayListOf<Blog>()

        for (blog in blogs) {
            if (blog.blogTitle.contains(strTyped)) {
                filteredList.add(blog)
            }
        }
        blogsAdapter.submitList(filteredList)
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
                    blogs = it.data?.blogs!!
                    val discussion = it.data.dcs
                    blogsAdapter.submitList(blogs)
                    discussionAdapter.submitList(discussion)
                    binding.animationView.visibility = View.GONE
                    binding.blogsList.visibility = View.VISIBLE

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
//        findNavController().navigate(
//            R.id.action_navigation_blogs_to_blogDetails,
//            bundleOf("id" to blog.blogId.toString())
//        )
        startWebActivity(blog.blogId.toString())

    }

    private fun onItemClicked(dcs: Dcs) {
        findNavController().navigate(
            R.id.action_navigation_blogs_to_discussionDetails,
            bundleOf("id" to dcs.dId.toString())
        )
//        startWebActivity(blog.blogId.toString())

    }


    private fun startWebActivity(blogId: String) {
        val intent = Intent(requireContext(), BlogWebPage::class.java)
        intent.putExtra("url", "https://gurumantra.online/api/webShowBlog/$blogId")
        intent.putExtra("id", blogId)
        startActivity(intent)
    }

}