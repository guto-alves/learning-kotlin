package com.example.coroutinesasyncawaitchallenge

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.security.SecureRandom

class MainActivity : AppCompatActivity() {
    private val random = SecureRandom()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startButton.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            textView.text = ""

            CoroutineScope(Main).launch {
                val deferred1 = async(IO) { downloadString() }
                val deferred2 = async(IO) { downloadInt() }
                val deferred3 = async(IO) { downloadBoolean() }

                val result = "${deferred1.await()}, ${deferred2.await()}, ${deferred3.await()}"

                progressBar.visibility = View.GONE

                textView.text = "Downloaded Data: $result"
            }
        }
    }

    private suspend fun downloadString(): String {
        delay(5000)
        return "Hello World"
    }

    private suspend fun downloadInt(): Int {
        delay(12000)
        return random.nextInt(10) + 1
    }

    private suspend fun downloadBoolean(): Boolean {
        delay(7000)
        return random.nextBoolean()
    }
}