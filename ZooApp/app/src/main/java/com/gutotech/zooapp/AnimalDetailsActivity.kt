package com.gutotech.zooapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_animal_details.*

class AnimalDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animal_details)

        val animal = intent.extras?.getSerializable("animal") as Animal

        imageView.setImageResource(animal.image)
        nameTextView.text = animal.name
        descriptionTextView.text = animal.description
    }
}