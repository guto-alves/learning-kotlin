package com.anushka.coroutinesdemo1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnCount.setOnClickListener {
            tvCount.text = count++.toString()
        }

        btnDownloadUserData.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                downloadUserData()
                delay(100)
            }
        }
    }

    private var i = 0

    private suspend fun downloadUserData() {
        while (i++ < 200000) {
            withContext(Dispatchers.Main) {
                tvUserMessage.text =
                    "Downloading user $i in ${Thread.currentThread().name}"
            }

            delay(1000)
        }
    }
}
