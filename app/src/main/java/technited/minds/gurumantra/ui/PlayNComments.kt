package technited.minds.gurumantra.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.navArgs
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import dagger.hilt.android.AndroidEntryPoint
import technited.minds.gurumantra.R
import technited.minds.gurumantra.databinding.ActivityPlayNCommentsBinding
import technited.minds.gurumantra.utils.Resource
import technited.minds.gurumantra.utils.SharedPrefs
import javax.inject.Inject

@AndroidEntryPoint
class PlayNComments : AppCompatActivity() {
    private lateinit var videoUrl: String
    private lateinit var youTubePlayerView: YouTubePlayerView
    private lateinit var binding: ActivityPlayNCommentsBinding
    private val args: PlayNCommentsArgs by navArgs()

    @Inject
    lateinit var userSharedPreferences: SharedPrefs
    private val liveViewModel: LiveViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_GuruMantra)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_play_n_comments)
        youTubePlayerView = binding.youtube
        lifecycle.addObserver(youTubePlayerView)
//        liveViewModel.fetchMeeting(userSharedPreferences["id"]!!, args.classNo, 1)
        if (args.url == "live")
            liveViewModel.getJoinLiveClass(args.classNo)
        else {
            videoUrl = args.url
            callListener()
        }
        setUpObservers()
    }

    private fun setUpObservers() {
        liveViewModel.joinLive.observe(this, {
            when (it.status) {
                Resource.Status.LOADING -> {
                    binding.animationView.visibility = View.VISIBLE

                }
                Resource.Status.SUCCESS -> {
                    val joinLiveClass = it.data

                    if (joinLiveClass != null) {

                        if (joinLiveClass.status == 1) {
                            videoUrl = joinLiveClass.cls.pcVideo
                            binding.animationView.visibility = View.GONE
                            callListener()
                        } else
                            Toast.makeText(this, joinLiveClass.message, Toast.LENGTH_SHORT).show()
                    }

                }
                Resource.Status.ERROR -> {
                    binding.animationView.visibility = View.GONE

                }

            }
        })
    }

    private fun callListener() {
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