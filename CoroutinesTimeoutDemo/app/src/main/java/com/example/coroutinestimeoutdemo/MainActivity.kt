package com.example.coroutinestimeoutdemo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        CoroutineScope(Dispatchers.Main).launch {
            val result = withTimeoutOrNull(3000) {
                repeat(1000) { i ->
                    Log.d("MyTag", i.toString())
                    delay(1000)
                }
                true
            }

            if (result == null) {
                Log.d("MyTag", "Timeout Cancellation Exception")
            } else {
                Log.d("MyTag", result.toString())
            }
        }
    }
}