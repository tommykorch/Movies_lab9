package com.bignerdranch.android.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class MovieViewModel: ViewModel() {
    val movieItemLiveData: LiveData<List<Movie>>
    init {
        movieItemLiveData =
            SearchActivity().searchContents()
    }
}