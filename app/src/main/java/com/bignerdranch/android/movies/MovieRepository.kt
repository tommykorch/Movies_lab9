package com.bignerdranch.android.movies
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.bignerdranch.android.movies.database.MovieDatabase
import java.util.UUID
import java.util.concurrent.Executors
private const val DATABASE_NAME = "movie-database"
class MovieRepository private  constructor(context: Context){
    private val database : MovieDatabase =
        Room.databaseBuilder(
            context.applicationContext,
            MovieDatabase::class.java,
            DATABASE_NAME
        ).build()
    private val movieDao = database.movieDao()
    private val executor = Executors.newSingleThreadExecutor()

    fun getMovies(): LiveData<List<Movie>> = movieDao.getMovies()
    fun getMovie(id: UUID): LiveData<Movie?> = movieDao.getMovie(id)
    fun updateMovie(movie: Movie) {
        executor.execute {
            movieDao.updateMovie(movie)
        }
    }
    fun addMovie(movie: Movie) {
        executor.execute {
            movieDao.addMovie(movie)
        }
    }

    companion object {
        private var INSTANCE: MovieRepository? = null
        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = MovieRepository(context)
            }
        }
        fun get(): MovieRepository {
            return INSTANCE ?:
            throw
            IllegalStateException("MovieRepository must be initialized")
        }
    }
}