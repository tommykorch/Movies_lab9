package com.bignerdranch.android.movies.network

import com.bignerdranch.android.movies.Movie
import com.google.gson.annotations.SerializedName

class MovieResponse {
    @SerializedName("movie")
    lateinit var movieItems: List<Movie>
}