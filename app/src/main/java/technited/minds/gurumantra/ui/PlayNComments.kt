package technited.minds.gurumantra.ui

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.navArgs
import com.afollestad.materialdialogs.MaterialDialog
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerFullScreenListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import dagger.hilt.android.AndroidEntryPoint
import technited.minds.gurumantra.R
import technited.minds.gurumantra.databinding.ActivityPlayNCommentsBinding
import technited.minds.gurumantra.ui.adapters.CommentsAdapter
import technited.minds.gurumantra.ui.blogs.CommentsViewModel
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
    private val commentsViewModel: CommentsViewModel by viewModels()
    private val commentsAdapter = CommentsAdapter()
    private var clsId = "0"
    private var type = "0"

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
        clsId = args.classNo
        type = args.type
        setupRecyclerView()
        setUpObservers()
        binding.postButton.setOnClickListener {
            if (binding.postComment.text.isNotEmpty()) {
                commentsViewModel.postComment(
                    userSharedPreferences["id"]!!.toInt(),
                    clsId.toInt(),
                    binding.postComment.text.toString(),
                    type
                )
            }
        }
    }

    private fun setupRecyclerView() {
        binding.commentsList.adapter = commentsAdapter
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

        commentsViewModel.getComments(clsId, type)

        commentsViewModel.comment.observe(this, {
            when (it.status) {
                Resource.Status.LOADING -> {
                }
                Resource.Status.SUCCESS -> {
                    val comments = it.data

                    if (comments != null) {
                        binding.commentsList.visibility = View.VISIBLE
                        commentsAdapter.submitList(comments.comment)

                    }
                }
                Resource.Status.ERROR -> {
                    MaterialDialog(this@PlayNComments).show {
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

        commentsViewModel.response.observe(this, {
            if (it.data != null) {
                if (it.data.data == 1) {
                    Toast.makeText(this@PlayNComments, "Comment Posted Successfully", Toast.LENGTH_SHORT).show()
                    binding.postComment.setText("")
                    commentsViewModel.getComments(clsId, type)
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
                binding.animationView.visibility = View.GONE
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
        youTubePlayerView.addFullScreenListener(object : YouTubePlayerFullScreenListener {
            override fun onYouTubePlayerEnterFullScreen() {
                (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        or View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        or View.SYSTEM_UI_FLAG_IMMERSIVE).also { window.decorView.systemUiVisibility = it }
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            }

            override fun onYouTubePlayerExitFullScreen() {
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
            }
        })
    }


    override fun onDestroy() {
        super.onDestroy()
        youTubePlayerView.release()
    }

}