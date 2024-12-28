package com.bignerdranch.android.movies.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.bignerdranch.android.movies.Movie
import retrofit2.http.DELETE
import java.util.UUID

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie")
    fun getMovies(): LiveData<List<Movie>>

    @Query("SELECT * FROM movie WHERE id=(:id)")
    fun getMovie(id: UUID): LiveData<Movie?>
    @Insert
    fun addMovie(movie: Movie)
    @Query("DELETE FROM movie WHERE id = :id")
    fun delete(id: Int?)
    @Update
    fun updateMovie(movie: Movie)
}