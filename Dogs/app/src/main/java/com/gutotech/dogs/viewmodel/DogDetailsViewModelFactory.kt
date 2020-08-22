package com.gutotech.dogs.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gutotech.dogs.model.Dog

class DogDetailsViewModelFactory(private val dog: Dog) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DogDetailsViewModel::class.java)) {
            return DogDetailsViewModel(dog) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}