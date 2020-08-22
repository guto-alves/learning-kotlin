package com.gutotech.dogs.api

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class DogServiceApi {

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    fun getDogService(): DogService = retrofit.create(
        DogService::class.java)

    companion object {
        private const val BASE_URL = "https://raw.githubusercontent.com/"
    }
}