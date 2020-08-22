package com.gutotech.dogs.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.gutotech.dogs.model.Dog

@Dao
interface DogDao {
    @Insert
    suspend fun insertAll(vararg dogs: Dog)

    @Query(value = "DELETE FROM dog")
    suspend fun deleteAll()

    @Query(value = "SELECT * FROM dog")
    suspend fun findAll(): List<Dog>
}