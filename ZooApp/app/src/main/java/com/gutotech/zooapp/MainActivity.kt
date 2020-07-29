package com.gutotech.zooapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = AnimalAdapter(listOf(
            Animal("Baboon", getString(R.string.animals_description_test), R.drawable.baboon),
            Animal("Bulldog", getString(R.string.animals_description_test), R.drawable.bulldog),
            Animal("Panda", getString(R.string.animals_description_test), R.drawable.panda),
            Animal("Swallow", getString(R.string.animals_description_test), R.drawable.swallow_bird),
            Animal("Tiger", getString(R.string.animals_description_test), R.drawable.white_tiger),
            Animal("Zebra", getString(R.string.animals_description_test), R.drawable.zebra)
        ), object : AnimalListener {
            override fun onClick(animal: Animal) {
                val intent = Intent(applicationContext, AnimalDetailsActivity::class.java)
                intent.putExtra("animal", animal)
                startActivity(intent)
            }
        })

        animalsRecyclerView.adapter = adapter
    }
}