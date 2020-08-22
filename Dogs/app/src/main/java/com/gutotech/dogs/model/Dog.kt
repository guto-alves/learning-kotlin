package com.gutotech.dogs.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity
data class Dog(
    @PrimaryKey
    val id: String,

    val name: String,

    val origin: String,

    @ColumnInfo(name = "life_span")
    @SerializedName("life_span")
    val lifeSpan: String,

    @ColumnInfo(name = "bred_for")
    @SerializedName("bred_for")
    val bredFor: String,

    @ColumnInfo(name = "breed_group")
    @SerializedName("breed_group")
    val breedGroup: String,

    val temperament: String,

    @ColumnInfo(name = "image_url")
    @SerializedName("url")
    val imageUrl: String
) : Serializable