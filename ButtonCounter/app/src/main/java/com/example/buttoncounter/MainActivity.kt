package com.example.buttoncounter

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView.movementMethod = ScrollingMovementMethod()

        button.setOnClickListener {
            if (editText.text.isNotBlank()) {
                textView.append("${editText.text}\n")
                editText.setText("")
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            putString(TEXT_CONTENTS, textView.text.toString())
        }

        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        savedInstanceState.run {
            textView.text = getString(TEXT_CONTENTS, "")
        }

        super.onRestoreInstanceState(savedInstanceState)
    }

    companion object {
        const val TEXT_CONTENTS = "textContents"
    }
}