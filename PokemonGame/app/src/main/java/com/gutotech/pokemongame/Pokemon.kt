package com.gutotech.pokemongame

import android.location.Location

class Pokemon(
    image: Int,
    name: String,
    description: String,
    power: Double,
    lat: Double,
    lng: Double
) {
    var image: Int? = image
    var name: String? = name
    var description: String? = description
    var power: Double? = power
    var location: Location? = null
    var isCatched = false

    init {
        location = Location(name)
        location!!.latitude = lat
        location!!.longitude = lng
    }
}