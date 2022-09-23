package com.example.animerecommender

import android.os.Build
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import kotlinx.android.synthetic.main.activity_trailer.*


class TrailerActivity : YouTubeBaseActivity() {

    var onInitializedListener: YouTubePlayer.OnInitializedListener? = null

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trailer)

       val movieTrailerId = intent.getStringExtra("Url")



        onInitializedListener = object : YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(
                provider: YouTubePlayer.Provider,
                youTubePlayer: YouTubePlayer,
                b: Boolean,
            ) {
                youTubePlayer.loadVideo(movieTrailerId)
                youTubePlayer.setFullscreen(true)
            }

            override fun onInitializationFailure(
                provider: YouTubePlayer.Provider,
                youTubeInitializationResult: YouTubeInitializationResult,
            ) {
                Toast.makeText(applicationContext, "Video not available", Toast.LENGTH_SHORT).show()
            }
        }
        trailerLayout.initialize("AIzaSyCL3nP82ToAvuHO-OSFkcZY3W-tBMLuqPg", onInitializedListener)
    }


}