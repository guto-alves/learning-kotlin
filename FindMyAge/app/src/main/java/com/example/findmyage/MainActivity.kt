package com.example.findmyage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getAgeButton.setOnClickListener {
            val yearOfBirth = Integer.parseInt(yearOfBirthEditText.text.toString())
            val age = calculateAge(yearOfBirth)
            ageTextView.text = "Your age is $age years"
        }
    }

    private fun calculateAge(yearOfBirth: Int): Int {
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        return currentYear - yearOfBirth
    }
}