package technited.minds.gurumantra.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import technited.minds.gurumantra.R
import technited.minds.gurumantra.databinding.ActivityWebPageBinding


class WebPage : AppCompatActivity() {
    private var backPressedOnce = false
    private var back = true
    private lateinit var binding: ActivityWebPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_GuruMantra)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_web_page)
        val webSettings = binding.webView.settings
        webSettings.javaScriptEnabled = true
        webSettings.setSupportZoom(true)
        webSettings.useWideViewPort = true
        webSettings.loadWithOverviewMode = true
        webSettings.domStorageEnabled = true
        webSettings.allowContentAccess = true
        intent.getStringExtra("url")?.let { binding.webView.loadUrl(it) }
        back = intent.getBooleanExtra("back", true)
        binding.webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                view.loadUrl(request.url.toString())
                return false
            }
        }
        binding.webView.setOnLongClickListener { true }
        webSettings.javaScriptCanOpenWindowsAutomatically = false;
        webSettings.setSupportMultipleWindows(false)
        binding.webView.isLongClickable = false
        binding.root.isLongClickable = false


    }

    override fun onBackPressed() {
        if (back) {
            finish()
        }
        back = true
        Toast.makeText(this, "Please back again to exit", Toast.LENGTH_SHORT).show()
        Handler(Looper.getMainLooper()).postDelayed({
            back = false
        }, 2000)

    }
}