package com.gutotech.viewmodeldemo2

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel(startingTotal: Int) : ViewModel(), Observable {
    private val total = MutableLiveData<Int>()

    @Bindable
    val number = MutableLiveData<String>()

    init {
        total.value = startingTotal
    }

    fun getTotal(): LiveData<Int> = total

    fun add() {
        val inputNumber: String? = number.value

        if (inputNumber != null && inputNumber.isNotEmpty()) {
            total.value = total.value?.plus(inputNumber.toInt())
        }
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}
