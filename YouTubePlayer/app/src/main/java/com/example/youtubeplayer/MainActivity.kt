package com.example.youtubeplayer

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myPlayerButton.setOnClickListener(this)
        standalonePlayerButton.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        val intent = when (view.id) {
            R.id.myPlayerButton -> Intent(this, MyPlayerActivity::class.java)
            R.id.standalonePlayerButton -> Intent(this, StandalonePlayerActivity::class.java)
            else -> throw IllegalArgumentException("Undefined button clicked")
        }

        startActivity(intent)
    }
}