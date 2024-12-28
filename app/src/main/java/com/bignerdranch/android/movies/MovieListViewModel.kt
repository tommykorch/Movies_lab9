package com.bignerdranch.android.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bignerdranch.android.movies.network.RetrofitClient

class MovieListViewModel: ViewModel() {
    private val movieRepository = MovieRepository.get()
    val movieListLiveData = movieRepository.getMovies()
    fun addMovie(movie: Movie) {
        movieRepository.addMovie(movie)
    }
    val movieItemLiveData: LiveData<List<Movie>>
    init {
        movieItemLiveData =
            RetrofitClient.searchMovie()
    }
}