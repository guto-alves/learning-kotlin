package com.gutotech.dogs.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gutotech.dogs.model.Dog

class DogDetailsViewModel(dog: Dog) : ViewModel() {
    val dog = MutableLiveData<Dog>(dog)

}