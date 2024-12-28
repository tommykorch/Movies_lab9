package com.bignerdranch.android.movies

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import java.util.UUID

class AddActivity: AppCompatActivity() {
    private lateinit var movieTitle: EditText
    private lateinit var yearMovie: EditText
    private lateinit var poster: ImageView
    private lateinit var search: ImageButton
    private lateinit var add: Button
    private lateinit var url: String
    interface Callbacks {
        fun onMovieSelected(movieId: UUID)
    }
    private var callbacks: Callbacks? = null
    private lateinit var crimeRecyclerView: RecyclerView
    /*private var adapter: MainAdapter? = MainAdapter(emptyList())*/
    private val movieListViewModel:
            MovieListViewModel by lazy {
        ViewModelProviders.of(this).get(MovieListViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        movieTitle = findViewById(R.id.editTextTitle)
        yearMovie = findViewById(R.id.editTextYear)
        poster = findViewById(R.id.imageViewPoster)
        search = findViewById(R.id.imageButtonSearch)
        add = findViewById(R.id.buttonAddMovie)
        url = ""
        if (url != ""){
            Picasso.get()
                .load(url)
                .placeholder(R.drawable.baseline_image_search_24)
                .error(R.drawable.baseline_hide_image_24)
                .into(poster);
        }
        search.setOnClickListener(){
            if (movieTitle.text.toString() == ""){
                Toast.makeText(this, "No text to search", Toast.LENGTH_SHORT).show()
            }
            else {
                val title = movieTitle.text.toString()
                val year = yearMovie.text.toString()
                val intent = SearchActivity.newIntent(this@AddActivity, title, year)
                startActivityForResult(intent, SEARCH_MOVIE_ACTIVITY_REQUEST_CODE)
            }
        }
        add.setOnClickListener(){

        }
    }
    companion object {
        const val SEARCH_MOVIE_ACTIVITY_REQUEST_CODE = 2
    }
}