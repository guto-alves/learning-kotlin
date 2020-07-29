package com.gutotech.zooapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AnimalAdapter(private val animals: List<Animal>, private val animalListener: AnimalListener) :
    RecyclerView.Adapter<AnimalAdapter.MyViewHolder>() {

    class MyViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView? = null
        var nameTextView: TextView? = null
        var descriptionTextView: TextView? = null

        init {
            imageView = itemView.findViewById(R.id.imageView)
            nameTextView = itemView.findViewById(R.id.nameTextView)
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView)
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val itemView: View = layoutInflater
                    .inflate(R.layout.animal_item, parent, false)
                return MyViewHolder(itemView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun getItemCount() = animals.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val animal = animals[position]
        holder.imageView?.setImageResource(animal.image)
        holder.nameTextView?.text = animal.name
        holder.descriptionTextView?.text = animal.description
        holder.itemView.setOnClickListener {
            animalListener.onClick(animal)
        }
    }
}

interface AnimalListener {
    fun onClick(animal: Animal)
}