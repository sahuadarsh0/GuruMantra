package technited.minds.gurumantra.ui.blogs

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.skydoves.powerspinner.PowerSpinnerView
import dagger.hilt.android.AndroidEntryPoint
import technited.minds.gurumantra.R
import technited.minds.gurumantra.databinding.FragmentBlogsBinding
import technited.minds.gurumantra.model.Blog
import technited.minds.gurumantra.model.Ct
import technited.minds.gurumantra.model.Dcs
import technited.minds.gurumantra.model.Sct
import technited.minds.gurumantra.ui.CategoryViewModel
import technited.minds.gurumantra.ui.adapters.BlogsAdapter
import technited.minds.gurumantra.ui.adapters.DiscussionAdapter
import technited.minds.gurumantra.utils.Resource

@AndroidEntryPoint
class BlogsFragment : Fragment() {

    private var _binding: FragmentBlogsBinding? = null

    private val blogsViewModel: BlogsViewModel by viewModels()
    private val categoryViewModel: CategoryViewModel by viewModels()
    private val blogsAdapter = BlogsAdapter(this::onItemClicked)
    private val discussionAdapter = DiscussionAdapter(this::onItemClicked)
    private val binding get() = _binding!!
    private lateinit var blogs: List<Blog>
    private lateinit var filterDialog: Dialog
    private lateinit var filterCatView: PowerSpinnerView
    private lateinit var filterSubCatView: PowerSpinnerView
    private lateinit var apply: Button
    private lateinit var cts: List<Ct>
    private lateinit var scts: List<Sct>
    private var cId = ""
    private var scId = ""


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentBlogsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupRecyclerView()
        setupObservers()
        setupFilter()

        binding.searchBlog.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (!s.isNullOrEmpty()) {
                    setupSearch(s.toString())
                }
            }
        })

        binding.filter.setOnClickListener {
            filterDialog.show()
            categoryViewModel.category.observe(viewLifecycleOwner, {
                if (it.data != null) {
                    cts = it.data.cts
                    val names = arrayListOf<String>()
                    cts.forEach { names.add(it.cId.toString() + " " + it.cName) }
                    filterCatView.setItems(names)

                }
            })
        }

        filterCatView.setOnSpinnerItemSelectedListener<String> { oldIndex, oldItem, newIndex, newItem ->
            cId = cts[newIndex].cId.toString()
            categoryViewModel.getSubCategory(cId)
        }
        filterSubCatView.setOnSpinnerItemSelectedListener<String> { oldIndex, oldItem, newIndex, newItem ->
            scId = scts[newIndex].scId.toString()
        }
        categoryViewModel.subCategory.observe(viewLifecycleOwner, {
            if (it.data != null) {
                scts = it.data.scts
                val names = arrayListOf<String>()
                scts.forEach { names.add(it.scId.toString() + " " + it.scName) }
                filterSubCatView.setItems(names)
            }
        })
        apply.setOnClickListener {
            blogsViewModel.filterBlogs(cId, scId)
            filterDialog.dismiss()
        }

        return root
    }

    private fun setupRecyclerView() {
        binding.blogsList.adapter = blogsAdapter
        binding.discussionList.adapter = discussionAdapter
    }


    fun setupSearch(strTyped: String) {
        val filteredList = arrayListOf<Blog>()

        for (blog in blogs) {
            if (blog.blogTitle.contains(strTyped)) {
                filteredList.add(blog)
            }else if (blog.blogTitle.lowercase().contains(strTyped.lowercase())) {
                filteredList.add(blog)
            }
        }
        blogsAdapter.submitList(filteredList)
    }

    private fun setupFilter() {
        filterDialog = Dialog(requireActivity())
        filterDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        filterDialog.setContentView(R.layout.dialog_filter_category)
        val window: Window? = filterDialog.window
        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        window?.setBackgroundDrawableResource(R.color.transparent)
        filterDialog.setCancelable(true)
        filterCatView = this.filterDialog.findViewById(R.id.category)
        filterSubCatView = this.filterDialog.findViewById(R.id.sub_category)
        apply = this.filterDialog.findViewById(R.id.apply)
    }

    private fun setupObservers() {
        binding.animationView.visibility = View.VISIBLE
        binding.blogsList.visibility = View.GONE
        blogsViewModel.getBlogs()
        blogsViewModel.blogs.observe(viewLifecycleOwner, {
            when (it.status) {
                Resource.Status.LOADING -> {
                    binding.animationView.visibility = View.VISIBLE
                    binding.blogsList.visibility = View.GONE

                }
                Resource.Status.SUCCESS -> {
                    blogs = it.data?.blogs!!
                    blogsAdapter.submitList(blogs)
                    if (it.data.dcs != null) {
                        val discussion = it.data.dcs
                        discussionAdapter.submitList(discussion)
                    }
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
        startWebActivity(blog.blogId.toString())
    }

    private fun onItemClicked(dcs: Dcs) {
        findNavController().navigate(
            R.id.action_navigation_blogs_to_discussionDetails,
            bundleOf("id" to dcs.dId.toString())
        )
    }


    private fun startWebActivity(blogId: String) {
        val intent = Intent(requireContext(), BlogWebPage::class.java)
        intent.putExtra("url", "https://gurumantra.online/api/webShowBlog/$blogId")
        intent.putExtra("id", blogId)
        startActivity(intent)
    }

}