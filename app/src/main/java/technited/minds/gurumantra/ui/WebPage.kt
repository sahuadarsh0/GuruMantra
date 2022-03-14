package technited.minds.gurumantra.ui

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import technited.minds.gurumantra.R
import technited.minds.gurumantra.databinding.ActivityWebPageBinding


class WebPage : AppCompatActivity() {
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
        binding.webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return false
            }
        }
        webSettings.javaScriptCanOpenWindowsAutomatically = false;
        webSettings.setSupportMultipleWindows(false);
    }
}