package com.bignerdranch.android.movies

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.wewatch.network.RetrofitClient
import com.squareup.picasso.Picasso
import java.util.HashSet

class MainAdapter(internal var movies: List<Movie>, internal var context: Context): RecyclerView.Adapter<MainAdapter.MovieHolder>() {
    val selectedMovies = HashSet<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.list_item_movie, parent, false)
        return MovieHolder(v)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        holder.titleTextView.text = movies[position].title
        holder.releaseDateTextView.text = movies[position].year
        if (movies[position].poster.equals("")) {
            holder.movieImageView.setImageDrawable(context.getDrawable(R.drawable.baseline_local_movies_24))
        } else {
            Picasso.get().load(RetrofitClient.OMDB_IMAGEURL + movies[position].poster).into(holder.movieImageView)
        }
    }

    override fun getItemCount(): Int {
        return movies.size
    }
    inner class MovieHolder(view: View): RecyclerView.ViewHolder(view){
        internal var titleTextView: TextView
        internal var releaseDateTextView: TextView
        internal var movieImageView: ImageView
        internal var checkBox: CheckBox

        init {
            titleTextView = view.findViewById(R.id.title)
            releaseDateTextView = view.findViewById(R.id.Year)
            movieImageView = view.findViewById(R.id.Poster)
            checkBox = view.findViewById(R.id.isWatched)
            checkBox.setOnClickListener {
                val adapterPosition = adapterPosition
                if (!selectedMovies.contains(movies[adapterPosition])) {
                    checkBox.isChecked = true
                    selectedMovies.add(movies[adapterPosition])
                } else {
                    checkBox.isChecked = false
                    selectedMovies.add(movies[adapterPosition])
                }
            }
        }
    }
}
