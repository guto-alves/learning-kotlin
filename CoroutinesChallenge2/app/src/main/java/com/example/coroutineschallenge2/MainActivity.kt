package com.example.coroutineschallenge2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private var count = 0
    private var userCount = 0
    private var locationCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userButton.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                downloadUserData()
            }
        }

        locationButton.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                downloadLocationData()
            }
        }

        countButton.setOnClickListener {
            countTextView.text = count++.toString()
        }
    }

    private suspend fun downloadUserData() {
        userCount = 0

        while (userCount++ < 200000) {
            withContext(Dispatchers.Main) {
                userTextView.text = "Downloading user $userCount in ${Thread.currentThread().name}"
            }
        }
    }


    private suspend fun downloadLocationData() {
        locationCount = 0

        while (locationCount++ < 200000) {
            withContext(Dispatchers.Main) {
                locationTextView.text =
                    "Downloading location $locationCount in ${Thread.currentThread().name}"
            }
        }
    }
}