package technited.minds.gurumantra.ui.blogs

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.afollestad.materialdialogs.MaterialDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import technited.minds.gurumantra.R
import technited.minds.gurumantra.databinding.ActivityBlogWebPageBinding
import technited.minds.gurumantra.model.Comment
import technited.minds.gurumantra.ui.adapters.CommentsAdapter
import technited.minds.gurumantra.utils.Resource
import technited.minds.gurumantra.utils.SharedPrefs
import javax.inject.Inject

@AndroidEntryPoint
class BlogWebPage : AppCompatActivity() {
    private lateinit var binding: ActivityBlogWebPageBinding
    private val commentsAdapter by lazy { CommentsAdapter(this::onItemClicked) }

    private val blogsViewModel: BlogsViewModel by viewModels()
    private lateinit var blogId: String

    @Inject
    lateinit var userSharedPreferences: SharedPrefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_GuruMantra)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_blog_web_page)
        val webSettings = binding.webView.settings
        webSettings.javaScriptEnabled = true
        webSettings.setSupportZoom(true)
        webSettings.useWideViewPort = true
        webSettings.loadWithOverviewMode = true
        webSettings.domStorageEnabled = true
        webSettings.allowContentAccess = true
        intent.getStringExtra("url")?.let { binding.webView.loadUrl(it) }
        binding.webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return false
            }
        }
        webSettings.javaScriptCanOpenWindowsAutomatically = false
        webSettings.setSupportMultipleWindows(false)
        setupObservers()
        setupRecyclerView()
        binding.postButton.setOnClickListener {
            if (binding.postComment.text.isNotEmpty()) {
                blogsViewModel.postComment(
                    userSharedPreferences["id"]!!.toInt(),
                    blogId.toInt(),
                    binding.postComment.text.toString()
                )
            }
        }
        CoroutineScope(Dispatchers.Main).launch {

            delay(2000.toLong())
            binding.commentsSection.visibility = View.VISIBLE
        }

    }

    private fun setupRecyclerView() {
        binding.commentsList.adapter = commentsAdapter
    }


    private fun setupObservers() {
        val id =  intent.getStringExtra("id")
        if (id != null) {
            blogId = id
            blogsViewModel.getComments(blogId)
        }
        blogsViewModel.comment.observe(this, {
            when (it.status) {
                Resource.Status.LOADING -> {
                }
                Resource.Status.SUCCESS -> {
                    val comments = it.data

                    if (comments != null) {

                        binding.commentsList.visibility = View.VISIBLE
                        commentsAdapter.submitList(comments)

                    }
                }
                Resource.Status.ERROR -> {
                    MaterialDialog(this@BlogWebPage).show {
                        title(text = "API ERROR")
                        message(text = it.message)
                        cornerRadius(16f)
                        positiveButton(text = "OK") { dialog ->
                            dialog.dismiss()
                        }
                    }

                }
            }
        })

        blogsViewModel.response.observe(this, {
            if (it.data != null) {
                if (it.data.data == 1) {
                    Toast.makeText(this@BlogWebPage, "Comment Posted Successfully", Toast.LENGTH_SHORT).show()
                    binding.postComment.setText("")
                    blogsViewModel.getComments(blogId)
                }
            }
        })

    }
    private fun onItemClicked(comment: Comment) {
    }
}