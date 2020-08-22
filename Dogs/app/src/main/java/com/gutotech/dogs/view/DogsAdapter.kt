package com.gutotech.dogs.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.gutotech.dogs.R
import com.gutotech.dogs.model.Dog
import com.gutotech.dogs.util.loadImage
import kotlinx.android.synthetic.main.dog_item.view.*

class DogsAdapter(private val dogs: ArrayList<Dog> = ArrayList()) :
    RecyclerView.Adapter<DogsAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.dog_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount() = dogs.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dog = dogs[position]

        holder.itemView.imageView.loadImage(dog.imageUrl)
        holder.itemView.nameTextView.text = dog.bredFor
        holder.itemView.lifeSpanTextView.text = dog.lifeSpan

        holder.itemView.setOnClickListener {
            val action = DogsFragmentDirections.actionDogsFragmentToDogDetailsFragment(dog)
            it.findNavController().navigate(action)
        }
    }

    fun updateDogList(newDogs: List<Dog>) {
        dogs.clear()
        dogs.addAll(newDogs)
        notifyDataSetChanged()
    }
}