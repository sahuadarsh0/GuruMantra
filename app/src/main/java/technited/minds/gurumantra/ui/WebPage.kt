package technited.minds.gurumantra.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import technited.minds.gurumantra.R
import technited.minds.gurumantra.databinding.ActivityMainBinding
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

    }
}