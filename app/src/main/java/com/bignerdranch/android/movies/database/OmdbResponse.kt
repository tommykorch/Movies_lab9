package com.bignerdranch.android.movies.database

import com.bignerdranch.android.movies.Movie
import com.bignerdranch.android.movies.network.MovieResponse
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class OmdbResponse {
    lateinit var movies: MovieResponse
}