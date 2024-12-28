package com.bignerdranch.android.movies.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bignerdranch.android.movies.Movie

@Database(entities = [ Movie::class ], version=1)
@TypeConverters(MovieTypeConverters::class)
abstract class MovieDatabase: RoomDatabase() {
    abstract fun movieDao(): MovieDao
}