package com.gutotech.dogs.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.gutotech.dogs.api.DogServiceApi
import com.gutotech.dogs.model.Dog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class DogsViewModel(application: Application) : BaseViewModel(application) {
    private val dogService = DogServiceApi().getDogService()

    private val compositeDisposable = CompositeDisposable()

    val dogs = MutableLiveData<List<Dog>>()
    val hasError = MutableLiveData<Boolean>()
    val isLoading = MutableLiveData<Boolean>()

    fun refresh() {
        fetchFromRemote()
    }

    private fun fetchFromDatabase() {

    }

    private fun fetchFromRemote() {
        isLoading.value = true
        hasError.value = false

        compositeDisposable.add(
            dogService.getDogs()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Dog>>() {
                    override fun onSuccess(t: List<Dog>) {
                        dogs.value = t
                        isLoading.value = false
                        hasError.value = false
                    }

                    override fun onError(e: Throwable) {
                        isLoading.value = false
                        hasError.value = true
                        e.printStackTrace()
                    }
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}