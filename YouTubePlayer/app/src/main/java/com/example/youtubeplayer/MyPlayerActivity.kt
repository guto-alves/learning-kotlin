package com.example.youtubeplayer

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import kotlinx.android.synthetic.main.activity_my_player.*

class MyPlayerActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_player)

        initializePlayer()
    }

    private fun initializePlayer() {
        youTubePlayerView.initialize(getString(R.string.GOOGLE_API_KEY), this)
    }

    override fun onInitializationSuccess(
        provider: YouTubePlayer.Provider?,
        player: YouTubePlayer?,
        wasRestored: Boolean
    ) {
        if (wasRestored) {
            player?.play()
        } else {
            player?.loadVideo(VIDEO_ID)
        }
    }

    override fun onInitializationFailure(
        provider: YouTubePlayer.Provider?,
        error: YouTubeInitializationResult?
    ) {
        if (error != null) {
            if (error.isUserRecoverableError) {
                error.getErrorDialog(this, 0).show()
            } else {
                val text = "There was an error initializing the YouTubePlayer ($error)"
                Toast.makeText(this, text, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == RESULT_OK) {
            initializePlayer()
        }
    }

    companion object {
        private const val VIDEO_ID = "wjvkOem-a_o"
    }
}