package com.gutotech.dogs.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.gutotech.dogs.model.Dog

@Database(entities = [Dog::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun dogDao(): DogDao

    companion object {
        private const val DATABASE_NAME = "dogs-db"

        @Volatile
        private var instance: AppDatabase? = null

        private val LOCK = Any()

        operator fun invoke(context: Context): AppDatabase {
            return instance ?: synchronized(LOCK) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase =
            Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .build()
    }
}