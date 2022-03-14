package technited.minds.gurumantra.ui

import android.app.PendingIntent.getActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.navArgs
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import technited.minds.gurumantra.R
import technited.minds.gurumantra.databinding.ActivityPlayBinding
import technited.minds.gurumantra.utils.SharedPrefs


class Play : AppCompatActivity() {
    private lateinit var videoUrl: String
    private lateinit var youTubePlayerView: YouTubePlayerView
    private lateinit var binding: ActivityPlayBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_GuruMantra)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_play)
        youTubePlayerView = findViewById(R.id.youtube)
        lifecycle.addObserver(youTubePlayerView)

        val args: PlayArgs by navArgs()
        videoUrl = args.url
        (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                or View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                or View.SYSTEM_UI_FLAG_IMMERSIVE).also { this.window.decorView.systemUiVisibility = it }
        youTubePlayerView.enterFullScreen()
        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {

            override fun onReady(youTubePlayer: YouTubePlayer) {
                super.onReady(youTubePlayer)
                youTubePlayer.loadVideo(videoUrl, 0F)
                youTubePlayer.play()

                youTubePlayer.addListener(object : AbstractYouTubePlayerListener() {
                    override fun onStateChange(
                        youTubePlayer: YouTubePlayer,
                        state: PlayerConstants.PlayerState
                    ) {
                        super.onStateChange(youTubePlayer, state)

                        if (state == PlayerConstants.PlayerState.ENDED) {


                        }

                    }
                })
            }
        })

    }

    override fun onDestroy() {
        super.onDestroy()
        youTubePlayerView.release()
    }

}