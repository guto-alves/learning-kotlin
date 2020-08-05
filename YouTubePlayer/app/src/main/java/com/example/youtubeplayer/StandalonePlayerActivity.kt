package com.example.youtubeplayer

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.youtube.player.YouTubeStandalonePlayer
import kotlinx.android.synthetic.main.activity_standalone_player.*

class StandalonePlayerActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_standalone_player)

        videoButton.setOnClickListener(this)
        playlistButton.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        val intent = when (view.id) {
            R.id.videoButton ->
                YouTubeStandalonePlayer.createVideoIntent(
                    this, getString(R.string.GOOGLE_API_KEY), Companion.VIDEO_ID
                )
            R.id.playlistButton -> YouTubeStandalonePlayer.createPlaylistIntent(
                this, getString(R.string.GOOGLE_API_KEY), Companion.PLAYLIST_ID
            )
            else -> throw IllegalArgumentException("Undefined button clicked")
        }

        startActivity(intent)
    }

    companion object {
        private const val VIDEO_ID = "dGghkjpNCQ8"
        private const val PLAYLIST_ID = "PL4o29bINVT4EG_y-k5jGoOu3-Am8Nvi10"
    }
}