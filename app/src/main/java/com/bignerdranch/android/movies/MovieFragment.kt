package com.bignerdranch.android.movies
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.movies.network.RetrofitClient
import com.bignerdranch.android.movies.network.RetrofitInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG = "MovieFragment"
class MovieFragment: Fragment() {
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var movieRecyclerView: RecyclerView
    override fun onCreate(savedInstanceState:
                          Bundle?) {
        super.onCreate(savedInstanceState)

        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view =
            inflater.inflate(R.layout.fragment_movie_list, container, false)
        movieRecyclerView =
            view.findViewById(R.id.photo_recycler_view)
        movieRecyclerView.layoutManager =
            GridLayoutManager(context, 3)
        return view
    }

    override fun onViewCreated(view: View,
                               savedInstanceState: Bundle?) {
        super.onViewCreated(view,
            savedInstanceState)
        movieViewModel.movieItemLiveData.observe(
            viewLifecycleOwner,
            Observer { movieItems ->
                movieRecyclerView.adapter = MovieAdapter(movieItems)
            })
    }

    private class MovieHolder(private val itemImageView: ImageView) : RecyclerView.ViewHolder(itemImageView)
    {
        val bindDrawable: (Drawable) -> Unit = itemImageView::setImageDrawable
    }
    private inner class MovieAdapter(private val movieItems: List<Movie>)
        : RecyclerView.Adapter<MovieHolder>() {
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): MovieHolder {
            val view = layoutInflater.inflate(
                R.layout.list_item_movie,
                parent,
                false
            ) as ImageView
            return MovieHolder(view)
        }
        override fun getItemCount(): Int =
            movieItems.size
        override fun onBindViewHolder(holder: MovieHolder, position: Int) {
            val movieItem = movieItems[position]
            val placeholder: Drawable = ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.baseline_image_search_24) ?: ColorDrawable()
            holder.bindDrawable(placeholder)
        }
    }


    companion object {
        fun newInstance() =
            MovieFragment()
    }

}