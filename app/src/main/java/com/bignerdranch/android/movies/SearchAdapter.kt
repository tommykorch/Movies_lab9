package com.bignerdranch.android.movies

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.movies.network.RetrofitInterface
import com.squareup.picasso.Picasso

class SearchAdapter(var movieList: List<Movie>, var context: Context, var listener: SearchActivity.RecyclerItemListener) : RecyclerView.Adapter<SearchAdapter.SearchMoviesHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchMoviesHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item_movie, parent, false)

        val viewHolder = SearchMoviesHolder(view)
        view.setOnClickListener { v -> listener.onItemClick(v, viewHolder.adapterPosition) }
        return viewHolder
    }

    override fun onBindViewHolder(holder: SearchMoviesHolder, position: Int) {

        holder.titleTextView.text = movieList[position].title
        holder.releaseDateTextView.text = movieList[position].year
        holder.overviewTextView.text = movieList[position].genre

        if (movieList[position].poster != null) {
            Picasso.get().load("http://www.omdbapi.com/?i=tt3896198&apikey=1596d355" + movieList[position].poster).into(holder.movieImageView)
        }

    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    fun getItemAtPosition(pos: Int): Movie {
        return movieList[pos]
    }

    inner class SearchMoviesHolder(v: View) : RecyclerView.ViewHolder(v) {

        var titleTextView: TextView = v.findViewById(R.id.title)
        var overviewTextView: TextView = v.findViewById(R.id.genres)
        var releaseDateTextView: TextView = v.findViewById(R.id.Year)
        var movieImageView: ImageView = v.findViewById(R.id.Poster)

        init {
            v.setOnClickListener { v: View ->
                listener.onItemClick(v, adapterPosition)
            }
        }
    }
}