package com.gutotech.dogs.api

import com.gutotech.dogs.model.Dog
import io.reactivex.Single
import retrofit2.http.GET

interface DogService {

    @GET("DevTides/DogsApi/master/dogs.json")
    fun getDogs(): Single<List<Dog>>
}